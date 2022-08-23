package com.betpawa.wallet.grpc;

import java.math.BigDecimal;
import java.util.Map;
import com.betpawa.wallet.api.proto.*;
import com.betpawa.wallet.dto.wallet.Balance;
import com.betpawa.wallet.dto.wallet.MoneyTransferData;
import com.betpawa.wallet.dto.wallet.MoneyTransferResult;
import com.betpawa.wallet.dto.wallet.TransferStatusType;
import com.betpawa.wallet.service.WalletService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class WalletGrpcService extends WalletGrpcServiceGrpc.WalletGrpcServiceImplBase {
    private final WalletService service;
    private final Map<TransferStatusType, TransferStatus> convertMap = Map.of(
            TransferStatusType.FAIL, TransferStatus.FAILURE,
            TransferStatusType.OK, TransferStatus.SUCCESS,
            TransferStatusType.NOT_ENOUGH_MONEY, TransferStatus.NOT_ENOUGH_MONEY
    );

    @Override
    public void reserveMoney(final MoneyTransferRequest request, final StreamObserver<MoneyTransferResponse> responseObserver) {
        responseObserver.onNext(convertTransferStatus(service.creditAccount(builderTransferData(request))));
        responseObserver.onCompleted();
    }

    private static MoneyTransferData builderTransferData(final MoneyTransferRequest request) {
        return new MoneyTransferData(request.getAccountId(), BigDecimal.valueOf(request.getAmount()), request.getReference());
    }

    @Override
    public void addMoney(final MoneyTransferRequest request, final StreamObserver<MoneyTransferResponse> responseObserver) {
        responseObserver.onNext(convertTransferStatus(service.debitAccount(builderTransferData(request))));
        responseObserver.onCompleted();
    }

    private MoneyTransferResponse convertTransferStatus(final MoneyTransferResult result) {
        return MoneyTransferResponse.newBuilder()
                .setAccountId(result.accountId())
                .setStatus(convertStatusType(result.status()))
                .setMessage(result.message())
                .build();
    }

    private TransferStatus convertStatusType(final TransferStatusType status) {
        return convertMap.get(status);
    }

    @Override
    public void balance(final AccountDetailsRequest request, final StreamObserver<BalanceResponse> responseObserver) {
        responseObserver.onNext(converBalance(service.balance(request.getAccountId())));
        responseObserver.onCompleted();
    }

    private BalanceResponse converBalance(final Balance balance) {
        return BalanceResponse.newBuilder()
                .setAccountId(balance.accountId())
                .setAmount(balance.amount().longValue())
                .build();
    }
}
