package com.shoe.shoemanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transaction")
public class TransactionDetails {
    @Id
    @Setter
    @Getter
    private String orderId;

    @Setter
    @Getter
    private String currency;

    @Setter
    @Getter
    private Integer amount;

    @Setter
    @Getter
    @Column(name = "`key`")  // Escape the 'key' column
    private String key;

    public TransactionDetails() {
    }

    public TransactionDetails(String orderId, String currency, Integer amount, String key) {
        this.orderId = orderId;
        this.currency = currency;
        this.amount = amount;
        this.key = key;
    }
}

