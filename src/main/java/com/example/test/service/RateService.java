package com.example.test.service;

import java.math.BigInteger;

import com.example.test.bean.ExchangeFilter;
import com.example.test.model.QRate;
import com.example.test.model.Rate;
import com.example.test.repo.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.repo.RateRepo;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class RateService {

	@Autowired
	private RateRepo rateRepo;

	@Autowired
	private CurrencyRepo currencyRepo;
	
	public Rate findByFiltered(ExchangeFilter filter){

		QRate rate = QRate.rate1;
		BigInteger curr1Id = currencyRepo.findByName(filter.getFrom()).getId();
		BigInteger curr2Id = currencyRepo.findByName(filter.getTo()).getId();

		BooleanExpression predicate = rate.curr1.id.eq(curr1Id).and(rate.curr2.id.eq(curr2Id));

		Rate res = rateRepo.findAll(predicate).iterator().next();
		res.setTo_amount(res.getRate() * filter.getFrom_amount());

		return res;
	}
}
