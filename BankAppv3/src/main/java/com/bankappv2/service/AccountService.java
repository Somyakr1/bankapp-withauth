package com.bankappv2.service;

import java.util.List;

import com.bankappv2.dto.AccountRequest;
import com.bankappv2.dto.AccountResponse;
import com.bankappv2.dto.AccountWithTxResponse;
import com.bankappv2.dto.DepositRequestDto;
import com.bankappv2.dto.TransferRequestDto;
import com.bankappv2.dto.WithdrawRequestDto;
import com.bankappv2.projection.AccountSummary;
import com.bankappv2.projection.TxView;


public interface AccountService {
	
	public AccountResponse addAccount(AccountRequest request);
	
    public void deleteAccount(int id);
    
    
    public void transfer(TransferRequestDto transferRequestDto);
    
    public void deposit(DepositRequestDto depositRequestDto);
    
    public void withdraw(WithdrawRequestDto withdrawRequestDto);
    

	public List<AccountResponse> getAll();

	public AccountResponse getById(int id);
   
   
    public List<AccountSummary> getAccountSummaries();
    
    public AccountSummary getAccountSummaryByAccountNumber(int id);
    
    
//    enter accId
    public AccountWithTxResponse getAccountWithTx(int id);

    
    public List<TxView> getAllTx();
    
//    enter txId
    public TxView getTxById(int id);
    
//    enter accId
    public long getTransactionCount(int id);
    
    
    
    public void approveWithdrawal(Long txId, boolean approve);

}
