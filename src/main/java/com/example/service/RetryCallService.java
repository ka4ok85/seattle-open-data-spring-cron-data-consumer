package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import com.example.entity.Call;

import feign.FeignException;

@Component
public class RetryCallService {
	private static final Logger log = LoggerFactory.getLogger(RetryCallService.class);

	@Value("${service.name}")
	private String serviceName;

	@Autowired
	private CallResource callResource;

	@Retryable(value = { FeignException.class }, maxAttempts = 3)
	public List<Call> requestCalls(String where) {
		List<Call> calls = callResource.calls(where);

		return calls;
	}

	@Recover
	public List<Call> requestWeatherRetryFallback(FeignException e) {
		log.warn("Service: {}. Can not fetch data from data.seattle.gov after 3 retries", serviceName);

		return new ArrayList<Call>();
	}
}