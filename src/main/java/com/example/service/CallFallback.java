package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.entity.Call;

@Component
public class CallFallback implements CallResource {

    private static final Logger log = LoggerFactory.getLogger(CallFallback.class);

    @Value("${service.name}")
    private String serviceName;

	@Override
	public List<Call> calls(String where) {

		log.error("Service: {}. Can not fetch 911 data", serviceName);
		return new ArrayList<Call>();
	}

}
