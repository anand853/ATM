package com.appstechs.beans;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "withdrawal")
public class Withdrawal {

	private int withdrawalID;
	private int amount;

	public int getWithdrawalID() {
		return withdrawalID;
	}

	public void setWithdrawalID(int withdrawalID) {
		this.withdrawalID = withdrawalID;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAccountDeatilsID() {
		return accountDeatilsID;
	}

	public void setAccountDeatilsID(int accountDeatilsID) {
		this.accountDeatilsID = accountDeatilsID;
	}

	private int accountDeatilsID;
}
