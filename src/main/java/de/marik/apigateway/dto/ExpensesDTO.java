package de.marik.apigateway.dto;

import java.time.LocalDate;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ExpensesDTO {
	
	private int id;

	@DecimalMin(value = "0.0", message = "amount should be a positive number")
	private double amount; // or replace with Double?

	@NotNull(message = "date should not be empty")
	@Temporal(TemporalType.DATE)
	private LocalDate date;

	@Size(max = 250, message = "purpose should not be longer than 250 symbols")
	private String comment;

	private int ownerIdentity;
	
	public ExpensesDTO() {
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getOwnerIdentity() {
		return ownerIdentity;
	}

	public void setOwnerIdentity(int ownerIdentity) {
		this.ownerIdentity = ownerIdentity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ExpensesDTO [amount=" + amount + ", date=" + date + ", comment=" + comment + ", ownerIdentity="
				+ ownerIdentity + "]";
	}
}
