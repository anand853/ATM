package com.appstechs.beans;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "deposit")
public class Deposit {
	private int depoistId;
	private int amount;
	private int accountDeatilsID;

	public int getDepoistId() {
		return depoistId;
	}

	public void setDepoistId(int depoistId) {
		this.depoistId = depoistId;
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

}
