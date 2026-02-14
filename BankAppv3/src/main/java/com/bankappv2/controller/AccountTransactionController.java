package com.bankappv2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankappv2.dto.ApprovalDto;
import com.bankappv2.dto.DepositRequestDto;
import com.bankappv2.dto.TransferRequestDto;
import com.bankappv2.dto.WithdrawRequestDto;
import com.bankappv2.projection.TxView;
import com.bankappv2.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="v2/transactions")
public class AccountTransactionController {
	
	private AccountService accountService;

	public AccountTransactionController(AccountService accountService) {
		this.accountService = accountService;
	}

//	by both
	@PutMapping("transfer")
//	public String transfer(@Valid @RequestBody TransferRequestDto transferRequest) {
//		accountService.transfer(transferRequest);
//		return "fund transfer successfully";
//	}
	public ResponseEntity<Map<String, String>> transfer(
	        @Valid @RequestBody TransferRequestDto transferRequest) {

	    accountService.transfer(transferRequest);

	    Map<String, String> response = new HashMap<>();
	    response.put("message", "Fund transfer successful");

	    return ResponseEntity.ok(response); // 200
	}
	
	

//	by both
	@PutMapping("deposit")
//	public String deposit(@RequestBody DepositRequestDto depositRequest) {
//		accountService.deposit(depositRequest);
//		return "deposit successfully";
//	}
	public ResponseEntity<Map<String, String>> deposit(
	        @RequestBody DepositRequestDto depositRequest) {

	    accountService.deposit(depositRequest);

	    Map<String, String> response = new HashMap<>();
	    response.put("message", "Deposit successful");

	    return ResponseEntity.ok(response); // 200
	}
	
	

//	by both
	@PutMapping("withdraw")
//	public String withdraw(@RequestBody WithdrawRequestDto withdrawRequest) {
//		accountService.withdraw(withdrawRequest);
//		return "withdraw successfully";
//	}
	public ResponseEntity<Map<String, String>> withdraw(
	        @RequestBody WithdrawRequestDto withdrawRequest) {

	    accountService.withdraw(withdrawRequest);

	    Map<String, String> response = new HashMap<>();
	    response.put("message", "Withdraw successful");

	    return ResponseEntity.ok(response); // 200
	}
	
	

	@GetMapping
//    public List<TxView> getAllTransactions() {
//
//        List<TxView> transactions = accountService.getAllTx();
//
//        return transactions;
//    }
	public ResponseEntity<List<TxView>> getAllTransactions() {

	    List<TxView> transactions = accountService.getAllTx();

	    return ResponseEntity.ok(transactions); // 200
	}
	
	
	
	@GetMapping("/{id}")
//    public TxView getTransactionById(@PathVariable int id) {
//
//        TxView transaction = accountService.getTxById(id);
//
//        return transaction;
//    }
	public ResponseEntity<TxView> getTransactionById(@PathVariable int id) {

	    TxView transaction = accountService.getTxById(id);

	    return ResponseEntity.ok(transaction); // 200
	}
	
	

	@GetMapping("account/{id}/count")
//	public long getTransactionCount(@PathVariable int id) {
//		return accountService.getTransactionCount(id);
//	}
	public ResponseEntity<Map<String, Long>> getTransactionCount(@PathVariable int id) {

	    long count = accountService.getTransactionCount(id);

	    Map<String, Long> response = new HashMap<>();
	    response.put("transactionCount", count);

	    return ResponseEntity.ok(response); // 200
	}
	
	@PutMapping("approve-withdrawal")
//	public String approveWithdrawal(@RequestBody ApprovalDto dto) {
//	    accountService.approveWithdrawal(dto.transactionId(), dto.approve());
//	    return "Approval processed";
//	}
	public ResponseEntity<Map<String, String>> approveWithdrawal(
	        @RequestBody ApprovalDto dto) {

	    accountService.approveWithdrawal(dto.transactionId(), dto.approve());

	    Map<String, String> response = new HashMap<>();
	    response.put("message", "Approval processed successfully");

	    return ResponseEntity.ok(response); // 200
	}

}


