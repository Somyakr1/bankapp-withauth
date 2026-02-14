package com.bankappv2.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record DepositRequestDto(@NotNull int accountId,
		@Positive BigDecimal amount) {
}