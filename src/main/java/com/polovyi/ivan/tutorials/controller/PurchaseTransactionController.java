package com.polovyi.ivan.tutorials.controller;

import com.polovyi.ivan.tutorials.dto.PurchaseTransactionRequest;
import com.polovyi.ivan.tutorials.service.PurchaseTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public record PurchaseTransactionController(PurchaseTransactionService purchaseTransactionService) {

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(path = "/purchase-transactions")
    public void acceptPurchaseTransaction(@RequestBody PurchaseTransactionRequest purchaseTransactionRequest) {
        purchaseTransactionService.processRequest(purchaseTransactionRequest);
    }

}
