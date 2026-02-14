package com.bankappv2.dto;

import java.math.BigDecimal;

import com.bankappv2.dto.TransferRequestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record TransferRequestDto(@NotNull(message = "{transferRequest.fromAccountId.absent}")
@Positive(message = "{transferRequest.fromAccountId.positive}")
int fromAccountId,

@NotNull(message = "{transferRequest.toAccountId.absent}")
@Positive(message = "{transferRequest.toAccountId.positive}")
int toAccountId,

@NotNull(message = "{transferRequest.amount.absent}")
@Positive(message = "{transferRequest.amount.positive}")
BigDecimal amount,
String clerkId) {
}
