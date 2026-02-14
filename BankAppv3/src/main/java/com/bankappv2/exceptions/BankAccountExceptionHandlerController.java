package com.bankappv2.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BankAccountExceptionHandlerController {
	@ExceptionHandler(BankAccountNotFoundException.class)
	public ResponseEntity<ProblemDetail> handle404(BankAccountNotFoundException e){
		ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problemDetail.setTitle("Account Not Found");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("errorCode", "NOT_FOUND");

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
	}
}
