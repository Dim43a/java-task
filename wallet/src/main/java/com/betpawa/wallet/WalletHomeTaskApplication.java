package com.betpawa.wallet;

import com.betpawa.wallet.grpc.WalletGrpcService;
import com.betpawa.wallet.service.WalletService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class WalletHomeTaskApplication {

	static WalletService service;

	public WalletHomeTaskApplication(WalletService service) {
		WalletHomeTaskApplication.service = service;
	}


	private static final Logger logger = Logger.getLogger(WalletHomeTaskApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(WalletHomeTaskApplication.class, args);

		Server server = ServerBuilder.forPort(4565)
				.addService(new WalletGrpcService(service) {
				})
				.build();

		try {
			server.start();
			logger.log(Level.INFO, "Server is running on the 4565 port");

			server.awaitTermination();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Server is not started");
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, "Server is shut down");
		}
	}
}
