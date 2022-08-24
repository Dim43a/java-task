package com.betpawa.bet;

import com.betpawa.wallet.api.proto.AccountDetailsRequest;
import com.betpawa.wallet.api.proto.BalanceResponse;
import com.betpawa.wallet.api.proto.WalletGrpcServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class Spring {

	private static final Logger logger = Logger.getLogger(Spring.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(Spring.class, args);

		ManagedChannel channel = ManagedChannelBuilder.forAddress(
				"localhost", 4565).usePlaintext().build();

		WalletGrpcServiceGrpc.WalletGrpcServiceBlockingStub client = WalletGrpcServiceGrpc.newBlockingStub(channel);


		AccountDetailsRequest acc = AccountDetailsRequest.newBuilder().setAccountId(543L).build();


		BalanceResponse blc ;
		try{
			blc = client.balance(acc);
		} catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		}
	}
}