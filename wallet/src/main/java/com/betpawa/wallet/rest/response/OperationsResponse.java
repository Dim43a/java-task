package com.betpawa.wallet.rest.response;

import com.betpawa.wallet.model.wallet.Operations;
import java.util.List;

public record OperationsResponse(
        List<Operations> operations,
        boolean hasMore
) {
}
