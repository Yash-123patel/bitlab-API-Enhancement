package com.talentstream.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
 
 
public class CreateOrderRequest {
	@NotNull(message = "Please Provide Amount To Create Order")
	@Positive(message ="Amount Cannot be negetive" )
	private Double amount;
	@NotNull(message = "Please Provide Valid Recruiter Id to Create Order")
	private Long recruiterId;

	public CreateOrderRequest(Double amount, Long recruiterId) {
		super();
		this.amount = amount;
		this.recruiterId = recruiterId;
	}
	public CreateOrderRequest() {
		super();
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getRecruiterId() {
		return recruiterId;
	}
	public void setRecruiterId(Long recruiterId) {
		this.recruiterId = recruiterId;
	}
	@Override
	public String toString() {
		return "CreateOrderRequest [amount=" + amount + ", recruiter_id=" + recruiterId + "]";
	}

}

