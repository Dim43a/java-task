package com.betpawa.wallet.rest.response;

import java.math.BigDecimal;

public record OperationResponse (String status, String message, BigDecimal currentBalance) {

}
