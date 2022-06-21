package com.example.test.repo;

import java.math.BigInteger;
import java.util.Optional;

import com.example.test.model.Curr;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepo extends CrudRepository<Curr, BigInteger>{

	public Curr findByName(String name);
}
