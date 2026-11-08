package com.employee.system.impl;

import com.employee.system.model.AccountTransferRequest;
import com.employee.system.model.AccountTransferResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//@Component
//@Order(2)
public class ConcurrentAccountTransfer implements CommandLineRunner {

    private final AccountTransferService accountTransferService;

    public ConcurrentAccountTransfer(AccountTransferService accountTransferService) {
        this.accountTransferService = accountTransferService;
    }

    @Override
    public void run(String... args) throws Exception {

        int THREAD = 1;
        List<Long> accounts = List.of(1L, 2L, 3L);
        Random random = new Random();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<AccountTransferResponse>> futures = new ArrayList<>();

        BigDecimal openingBalance = accountTransferService.getTotalBalance();
        System.out.println("openingBalance:" + openingBalance);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(THREAD);

        for (int i = 0; i < THREAD; i++) {
            Long fromAccount = accounts.get(random.nextInt(accounts.size()));
            Long toAccount;
            do {
                toAccount = accounts.get(random.nextInt(accounts.size()));
            } while (toAccount.equals(fromAccount));


            AccountTransferRequest request = new AccountTransferRequest(
                    fromAccount, toAccount, new BigDecimal("500")
            );
            Future<AccountTransferResponse> future = executor.submit(() -> {
                try {
                    startLatch.await();   // wait for signal
                    return accountTransferService.transfer(request);
                } finally {
                    finishLatch.countDown();
                }
            });
            futures.add(future);

        }
        // Release all threads simultaneously
        startLatch.countDown();

        // Wait for all threads to finish
        finishLatch.await();

        executor.shutdown();
        for (int i = 0; i < futures.size(); i++) {
            try {
                AccountTransferResponse response = futures.get(i).get();
                System.out.println("Transfer " + (i + 1) + " committed: " + response.transactionId());
            } catch (ExecutionException exception) {
                System.err.println("Transfer " + (i + 1) + " failed: " + exception.getCause());
            }
        }

        BigDecimal closingBalance = accountTransferService.getTotalBalance();
        System.out.println("closingBalance:" + closingBalance);


    }
}
