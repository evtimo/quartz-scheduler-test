package com.example.test.job;

import com.example.test.model.Rate;
import com.example.test.model.Curr;
import com.example.test.repo.CurrencyRepo;
import com.example.test.repo.RateRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class UpdateDataJob implements Job {

	@Autowired
	private RateRepo rateRepo;

	@Autowired
	private CurrencyRepo currencyRepo;

	@Value("${currencies}")
	private String currencies;

	@Value("${apikey}")
	private String apikey;

	private final OkHttpClient client = new OkHttpClient();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("starting update data job execution");
		initCurrencies();
		for (String cur : currencies.split(",")) {
			try {
				Request request = new Request.Builder()
						.url("https://api.apilayer.com/exchangerates_data/latest?symbols=" + currencies + "&base=" + cur)
						.addHeader("apikey", apikey)
						.method("GET", null)
						.build();
				Response response = client.newCall(request).execute();
				String body = response.body().string();
				System.out.println(body);
				Map<String, Object> result = new ObjectMapper().readValue(body, HashMap.class);
				LinkedHashMap<String, Double> rates = (LinkedHashMap<String, Double>) result.get("rates");
				List<Rate> ratesToSave = rates.entrySet().stream().filter(rate -> !rate.getKey().equals(cur))
						.map(rate -> {
							Curr curr1 = currencyRepo.findByName(cur);
							Curr curr2 = currencyRepo.findByName(rate.getKey());
							if (rateRepo.findByCurr1AndCurr2(curr1, curr2).isPresent()) {
								rateRepo.updateRate(curr1.getId(), curr2.getId(), rate.getValue());
							} else {
								Rate newRate = new Rate();
								newRate.setCurr1(curr1);
								newRate.setCurr2(curr2);
								newRate.setRate(rate.getValue());
								return newRate;
							}
							return null;
						}).filter(Objects::nonNull).collect(Collectors.toList());
				rateRepo.saveAll(ratesToSave);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void initCurrencies() {

		Request request = new Request.Builder()
				.url("https://api.apilayer.com/exchangerates_data/symbols")
				.addHeader("apikey", apikey)
				.method("GET", null)
				.build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
		Map<String, Object> result = new ObjectMapper().readValue(response.body().string(), HashMap.class);
			LinkedHashMap<String, String> symbols = (LinkedHashMap<String, String>) result.get("symbols");
			List<Curr> symbolsToSave = symbols.entrySet().stream()
					.map(symbol -> {
						Curr cur = currencyRepo.findByName(symbol.getKey());
						if (cur == null) {
							cur = new Curr();
							cur.setName(symbol.getKey());
						}
						return cur;
					}).collect(Collectors.toList());
			currencyRepo.saveAll(symbolsToSave);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
