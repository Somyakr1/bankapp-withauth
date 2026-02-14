package com.bankappv2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankappv2.dto.AccountRequest;
import com.bankappv2.dto.AccountResponse;
import com.bankappv2.dto.AccountWithTxResponse;
import com.bankappv2.projection.AccountSummary;
import com.bankappv2.service.AccountService;

@RestController
@RequestMapping(path="v2/accounts")
public class AccountController {
	private AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
//	only by mgr
	@PostMapping("add")
//	public String addAccount(@RequestBody AccountRequest accountRequest) {
//		accountService.addAccount(accountRequest);
//		return "Account created.";
//	}
	public ResponseEntity<AccountResponse> addAccount(@RequestBody AccountRequest accountRequest) {

	    AccountResponse response = accountService.addAccount(accountRequest);

	    return new ResponseEntity<>(response, HttpStatus.CREATED); // 201
	}
	
	

//	only by mgr
	@DeleteMapping("delete/{id}")
//    public String deleteAccount(@PathVariable int id) {
//		accountService.deleteAccount(id);
//		return "Account deleted.";
//	}
	public ResponseEntity<Map<String, String>> deleteAccount(@PathVariable int id) {

	    accountService.deleteAccount(id);

	    Map<String, String> response = new HashMap<>();
	    response.put("message", "Account deleted successfully");

	    return ResponseEntity.ok(response); // 200
	}
	
	
	
//	by both
	@GetMapping(path = "accounts")
//	public List<AccountResponse> getAll(){
//		return accountService.getAll();
//	}
	public ResponseEntity<List<AccountResponse>> getAll() {

	    List<AccountResponse> accounts = accountService.getAll();

	    return ResponseEntity.ok(accounts); // 200
	}
	
	
//	by both
	@GetMapping(path = "accounts/{id}")
//	public AccountResponse getById(@PathVariable(name="id")int id ){
//		return accountService.getById(id);
//	}
//	public AccountWithTxResponse getById(@PathVariable int id) {
//		return accountService.getAccountWithTx(id);
//	}
	public ResponseEntity<AccountResponse> getById(@PathVariable int id) {

	    AccountResponse account = accountService.getById(id);

	    return ResponseEntity.ok(account); // 200
	}
	
	
	
	
    @GetMapping("{id}/with-transactions")
//    public AccountWithTxResponse getAccountWithTransactions(@PathVariable int id) {
//        return accountService.getAccountWithTx(id);
//    }
    public ResponseEntity<AccountWithTxResponse> getAccountWithTransactions(@PathVariable int id) {

        AccountWithTxResponse response = accountService.getAccountWithTx(id);

        return ResponseEntity.ok(response); // 200
    }
    
    
    

    @GetMapping("summary")
//    public List<AccountSummary> getAccountSummaries() {
//        return accountService.getAccountSummaries();
//    }
    public ResponseEntity<List<AccountSummary>> getAccountSummaries() {

        return ResponseEntity.ok(accountService.getAccountSummaries());
    }
    
    

    @GetMapping("summary/by-account-number/{id}")
//    public AccountSummary getAccountSummaryByAccountNumber(
//            @PathVariable int id) {
//        return accountService.getAccountSummaryByAccountNumber(id);
//    }
    public ResponseEntity<AccountSummary> getAccountSummaryByAccountNumber(@PathVariable int id) {

        return ResponseEntity.ok(accountService.getAccountSummaryByAccountNumber(id));
    }
}


