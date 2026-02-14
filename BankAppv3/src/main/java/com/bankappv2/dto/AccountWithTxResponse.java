package com.bankappv2.dto;

import java.math.BigDecimal;
import java.util.List;

import com.bankappv2.projection.TxView;

public record AccountWithTxResponse(int id,
	    String name,
	    BigDecimal balance,
	    List<TxView> transactions) {

}
