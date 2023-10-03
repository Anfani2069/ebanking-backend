package com.bank.ebankingbackend.exceptions;

public class BankAccountNotFoundException extends Exception {

    //exception metier
    public BankAccountNotFoundException(String message) {
        super(message);
    }
}
