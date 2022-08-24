package com.betpawa.wallet.rest.response;

import com.betpawa.wallet.dto.wallet.TransferStatusType;
import java.math.BigDecimal;

public record OperationResponse (TransferStatusType status, String message, BigDecimal currentBalance) {

}
