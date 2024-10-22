package org.example.task7;

import java.util.HashSet;
import java.util.Set;

public class BankAccount {
	private static final Set<String> accountNumbers = new HashSet<>();

	private final String accountNumber;
	private final Bank bank;
	private final String currency;
	private double balance;

	public BankAccount(final String accountNumber, final Bank bank, final String currency, final double initialBalance) {
		if (!accountNumbers.add(accountNumber)) {
			throw new IllegalArgumentException("Account number must be unique");
		}
		this.accountNumber = accountNumber;
		this.bank = bank;
		this.currency = currency;
		this.balance = initialBalance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public Bank getBank() {
		return bank;
	}

	public String getCurrency() {
		return currency;
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(final double amount) {
		if (amount > 0) {
			balance += amount;
		} else {
			throw new IllegalArgumentException("Deposit amount must be positive");
		}
	}

	public boolean withdraw(final double amount) {
		if (amount > 0 && amount <= balance) {
			balance -= amount;
			return true;
		}
		return false;
	}
}