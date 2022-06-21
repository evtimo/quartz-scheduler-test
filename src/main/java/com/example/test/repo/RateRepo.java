package com.example.test.repo;

import java.math.BigInteger;
import java.util.Optional;

import com.example.test.model.Curr;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.example.test.model.Rate;

import javax.transaction.Transactional;

public interface RateRepo extends CrudRepository<Rate, BigInteger>, QuerydslPredicateExecutor<Rate>{

	@Transactional
	@Modifying
	@Query("UPDATE Rate r SET r.rate = ?3 WHERE r.curr1.id = ?1 AND r.curr2.id = ?2")
	public void updateRate(BigInteger curr1, BigInteger curr2, Double rate);

	public Optional<Rate> findByCurr1AndCurr2(Curr curr1, Curr curr2);

}
