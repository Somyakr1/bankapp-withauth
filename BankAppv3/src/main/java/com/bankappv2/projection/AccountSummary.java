package com.bankappv2.projection;

import java.math.BigDecimal;

public interface AccountSummary {
	Integer getId();
    String getName();
    BigDecimal getBalance();
}
