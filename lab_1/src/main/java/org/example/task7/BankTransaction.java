package org.example.task7;

import org.example.lab_1.task6.CurrencyConverter;

public class BankTransaction {
    private static final double SAME_BANK_INTERNAL_TRANSFER_FEE = 0.0;
    private static final double DIFFERENT_BANK_INTERNAL_TRANSFER_FEE = 0.02;
    private static final double SAME_BANK_EXTERNAL_TRANSFER_FEE = 0.03;
    private static final double DIFFERENT_BANK_EXTERNAL_TRANSFER_FEE = 0.06;

    private final CurrencyConverter currencyConverter = new CurrencyConverter();

    public boolean transfer(final BankAccount fromAccount, final BankAccount toAccount, final double amount) {
        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("Both accounts must be provided");
        }

        final double convertedAmount = convertAmount(amount, fromAccount.getCurrency(), toAccount.getCurrency());
        final double fee = calculateFee(fromAccount, toAccount, amount);

        if (fromAccount.withdraw(amount + fee)) {
            toAccount.deposit(convertedAmount);
            return true;
        } else {
            return false;
        }
    }

    private double convertAmount(final double amount, final String fromCurrency, final String toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            return amount;
        }
        final String input = amount + " " + fromCurrency + " into " + toCurrency;
        return currencyConverter.convertCurrency(input);
    }

    private double calculateFee(final BankAccount fromAccount, final BankAccount toAccount, final double amount) {
        if (fromAccount.getBank().equals(toAccount.getBank())) {
            if (fromAccount.getAccountNumber().equals(toAccount.getAccountNumber())) {
                return SAME_BANK_INTERNAL_TRANSFER_FEE * amount; // 0%
            } else {
                return SAME_BANK_EXTERNAL_TRANSFER_FEE * amount; // 3%
            }
        } else {
            if (fromAccount.getAccountNumber().equals(toAccount.getAccountNumber())) {
                return DIFFERENT_BANK_INTERNAL_TRANSFER_FEE * amount; // 2%
            } else {
                return DIFFERENT_BANK_EXTERNAL_TRANSFER_FEE * amount; // 6%
            }
        }
    }
}
