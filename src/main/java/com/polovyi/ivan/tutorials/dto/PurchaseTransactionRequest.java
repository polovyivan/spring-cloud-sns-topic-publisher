package com.polovyi.ivan.tutorials.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PurchaseTransactionRequest {

    private String paymentType;

    private BigDecimal amount;

    private String customerId;

}
