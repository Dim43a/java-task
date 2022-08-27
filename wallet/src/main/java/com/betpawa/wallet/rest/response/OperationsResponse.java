package com.betpawa.wallet.rest.response;

import com.betpawa.wallet.model.wallet.Operations;
import java.util.List;

public record OperationsResponse(
        String message,
        List<Operations> operations,
        boolean hasMore
) {
}
