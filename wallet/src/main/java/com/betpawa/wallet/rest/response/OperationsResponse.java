package com.betpawa.wallet.rest.response;

import com.betpawa.wallet.model.Operation;
import java.util.List;

public record OperationsResponse(
        List<Operation> operations,
        boolean hasMore
) {
}
