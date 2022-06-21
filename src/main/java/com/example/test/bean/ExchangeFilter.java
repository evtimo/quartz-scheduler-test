package com.example.test.bean;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Getter @Setter
public class ExchangeFilter {

	private String from;

	private String to;

	private Double from_amount;
}
