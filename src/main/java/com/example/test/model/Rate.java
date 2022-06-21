package com.example.test.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter @Setter
@Entity
@Table(name = "crate")
public class Rate extends BaseEntity{

	@ManyToOne
	@JoinColumn(name = "curr_1_id")
	private Curr curr1;

	@ManyToOne
	@JoinColumn(name = "curr_2_id")
	private Curr curr2;
	
	private Double rate;

	private Double to_amount;

	public Rate() {

	}
}
