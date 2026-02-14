package com.bankappv2.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TxIdExceptionHandlerController {
	@ExceptionHandler(TxIdNotFoundException.class)
	public ResponseEntity<ProblemDetail> handle404(TxIdNotFoundException e){
		ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problemDetail.setTitle("Transaction Not Found");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("errorCode", "NOT_FOUND");

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
	}
}
