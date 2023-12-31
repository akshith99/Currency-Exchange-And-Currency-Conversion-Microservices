package com.in28minutes.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	@Autowired
	private Environment environment;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(
			@PathVariable String from, 
			@PathVariable String to ) {
		
		//INFO [currency-exchange,c1cde29396731ef9c323e88854099b74,6a6136538332ca68] 18468 --- [nio-8000-exec-2] c.i.m.c.CurrencyExchangeController       : retrieve exchange value called with USD to INR
		logger.info("retrieve exchange value called with {} to {}", from, to);
		
		//CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));
		
		CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
		
		if(currencyExchange == null) {
			throw new RuntimeException("Unable to find data for " + from + " to " + to);
		}
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		return currencyExchange;
		
	}

}
