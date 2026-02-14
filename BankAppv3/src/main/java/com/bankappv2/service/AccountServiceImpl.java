package com.bankappv2.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankappv2.dto.AccountRequest;
import com.bankappv2.dto.AccountResponse;
import com.bankappv2.dto.AccountWithTxResponse;
import com.bankappv2.dto.DepositRequestDto;
import com.bankappv2.dto.TransferRequestDto;
import com.bankappv2.dto.WithdrawRequestDto;
import com.bankappv2.entity.Account;
import com.bankappv2.entity.BankTxType;
import com.bankappv2.entity.TxHistory;
import com.bankappv2.entity.TxStatus;
import com.bankappv2.exceptions.BankAccountNotFoundException;
import com.bankappv2.exceptions.TxIdNotFoundException;
import com.bankappv2.mapper.AccountMapper;
import com.bankappv2.projection.AccountSummary;
import com.bankappv2.projection.TxView;
import com.bankappv2.repo.AccountRepo;
import com.bankappv2.repo.TxHistoryRepo;

import lombok.AllArgsConstructor;


@Service 
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{
	
	private AccountRepo accountRepo;
	private TxHistoryRepo txHistoryRepo;
	private AccountMapper accountMapper;
	
	private Account getAccountEntity(int id) {
	    return accountRepo.findById(id)
	            .orElseThrow(() -> new BankAccountNotFoundException("Account not found: " + id));
	}
	
	private TxHistory buildTx(BankTxType type, BigDecimal amount, BigDecimal balanceAfter, Account account) {
		TxHistory tx = new TxHistory();
		tx.setType(type);
		tx.setAmount(amount);
		tx.setBalanceAfterTx(balanceAfter);
		tx.setTxTime(LocalDateTime.now());
		tx.setAccount(account);
		tx.setStatus(TxStatus.SUCCESS);
		return tx;
	}
	
	@PreAuthorize("hasRole('MGR')")
	@Override
	public AccountResponse addAccount(AccountRequest accountRequest) {
		Account account = accountMapper.toEntity(accountRequest);
		accountRepo.save(account);
		return accountMapper.toResponse(account);
	}
	
	
	@PreAuthorize("hasRole('MGR')")
	@Override
	public void deleteAccount(int id) {
        accountRepo.delete(getAccountEntity(id));
	}
	
	@PreAuthorize("hasAnyRole('CLERK','MGR')")
	@Override
	public void transfer(TransferRequestDto request) {
		
		Account from = getAccountEntity(request.fromAccountId());
		Account to = getAccountEntity(request.toAccountId());

		if (request.fromAccountId() == request.toAccountId()) {
			throw new RuntimeException("Source and destination accounts must be different");
		}
		
		if (from.getBalance().compareTo(request.amount()) < 0) {
			throw new RuntimeException("Insufficient balance");
		}

		// debit
		BigDecimal fromNewBal = from.getBalance().subtract(request.amount());
		from.setBalance(fromNewBal);

		txHistoryRepo.save(buildTx(BankTxType.TRANSFER_OUT, request.amount(), fromNewBal, from));

		// credit
		BigDecimal toNewBal = to.getBalance().add(request.amount());
		to.setBalance(toNewBal);

		txHistoryRepo.save(buildTx(BankTxType.TRANSFER_IN, request.amount(), toNewBal, to));

		accountRepo.save(from);
		accountRepo.save(to);
	}
	
	@PreAuthorize("hasAnyRole('CLERK','MGR')")
	@Override
	public void deposit(DepositRequestDto request) {

		Account account = getAccountEntity(request.accountId());

		BigDecimal newBalance = account.getBalance().add(request.amount());
		account.setBalance(newBalance);

		TxHistory tx = buildTx(BankTxType.DEPOSIT, request.amount(), newBalance, account);

		txHistoryRepo.save(tx);
		accountRepo.save(account);
	}
	
	@PreAuthorize("hasAnyRole('CLERK','MGR')")
	@Override
	public void withdraw(WithdrawRequestDto request) {

		Account account = getAccountEntity(request.accountId());

		if (account.getBalance().compareTo(request.amount()) < 0) {
			throw new RuntimeException("Insufficient balance");
		}
		
		 BigDecimal limit = new BigDecimal("200000");
		 
		// Case 1: Clerk can process directly
		    if (request.amount().compareTo(limit) <= 0) {

		        BigDecimal newBalance = account.getBalance().subtract(request.amount());
		        account.setBalance(newBalance);

		        TxHistory tx = buildTx(BankTxType.WITHDRAW, request.amount(), newBalance, account);
		        tx.setStatus(TxStatus.SUCCESS);

		        txHistoryRepo.save(tx);
		        accountRepo.save(account);

		    } else {

		        // Case 2: Needs manager approval
		        TxHistory tx = buildTx(BankTxType.WITHDRAW, request.amount(), account.getBalance(), account);
		        tx.setStatus(TxStatus.PENDING_APPROVAL);

		        txHistoryRepo.save(tx);
		    }
	}
	
	@PreAuthorize("hasAnyRole('CLERK','MGR')")
	@Override
	public List<AccountResponse> getAll(){
		return accountRepo.findAll().stream().map(accountMapper::toResponse).toList();
		}
	
	@PreAuthorize("hasAnyRole('CLERK','MGR')")
	@Override
	public AccountResponse getById(int id) {
		Account account = accountRepo.findById(id).orElseThrow(() -> new BankAccountNotFoundException("Account not found: " + id));

		return accountMapper.toResponse(account);
	}
	
	
	@PreAuthorize("hasAnyRole('CLERK','MGR')")
	@Override
	public List<AccountSummary> getAccountSummaries() {
		return accountRepo.findAccountSummaries();
	}

	@PreAuthorize("hasAnyRole('CLERK','MGR')")
	@Override
	public AccountSummary getAccountSummaryByAccountNumber(int id) {
		return accountRepo.findAccountSummaryById(id)
				.orElseThrow(() -> new BankAccountNotFoundException("Account not found: " + id));
	}

	
	@PreAuthorize("hasAnyRole('CLERK','MGR')")
	@Override
	public AccountWithTxResponse getAccountWithTx(int id) {

	    // 1. Load Account entity (no JSON here)
	    Account account = accountRepo.findById(id)
	            .orElseThrow(() -> new BankAccountNotFoundException("Account not found"));

	    // 2. Load transactions using projection (NO N+1)
	    List<TxView> transactions = txHistoryRepo.findTxByAccountId(id);

	    // 3. Combine into DTO
	    return new AccountWithTxResponse(
	            account.getId(),
	            account.getName(),
	            account.getBalance(),
	            transactions
	    );
	}
	
	@PreAuthorize("hasRole('MGR')")
	@Override
	public List<TxView> getAllTx() {
		List<TxView> txList = txHistoryRepo.findAllBy();

	    if (txList.isEmpty()) {
	        throw new RuntimeException("No transactions found");
	    }

	    return txList;
	}

	@PreAuthorize("hasRole('MGR')")
	@Override
	public TxView getTxById(int id) {
		return txHistoryRepo.findById(id).orElseThrow(() -> new TxIdNotFoundException("Transaction not found with id: " + id));
	}
	
	@PreAuthorize("hasRole('MGR')")
	@Override
	public long getTransactionCount(int id) {
		return txHistoryRepo.countTxForAccount(id);
	}
	
	
	@PreAuthorize("hasRole('MGR')")
	@Override
	public void approveWithdrawal(Long txId, boolean approve) {
		TxHistory tx = txHistoryRepo.findById(txId)
	            .orElseThrow(() -> new TxIdNotFoundException("Transaction not found"));

	    if (tx.getStatus() != TxStatus.PENDING_APPROVAL) {
	        throw new RuntimeException("Transaction is not pending approval");
	    }

	    Account account = tx.getAccount();

	    if (approve) {

	        if (account.getBalance().compareTo(tx.getAmount()) < 0) {
	        	tx.setStatus(TxStatus.REJECTED);
	            txHistoryRepo.save(tx);
	            return;
	        }

	        BigDecimal newBalance = account.getBalance().subtract(tx.getAmount());
	        account.setBalance(newBalance);

	        tx.setBalanceAfterTx(newBalance);
	        tx.setStatus(TxStatus.SUCCESS);

	        accountRepo.save(account);
	        txHistoryRepo.save(tx);	

	    } else {

	        tx.setStatus(TxStatus.REJECTED);
	        txHistoryRepo.save(tx);	
	    }

	    	
	}

	
	



}
