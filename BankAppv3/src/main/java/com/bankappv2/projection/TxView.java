package com.bankappv2.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bankappv2.entity.BankTxType;
import com.bankappv2.entity.TxStatus;

public interface TxView {
	BankTxType getType();
    BigDecimal getAmount();
    BigDecimal getBalanceAfterTx();
    LocalDateTime getTxTime();
    TxStatus getStatus();
}
