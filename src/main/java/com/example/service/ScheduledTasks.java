package com.example.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.entity.Call;
import com.example.repository.CallRepository;

@Component
public class ScheduledTasks {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	@Value("${service.name}")
	private String serviceName;

	@Autowired
	private CallRepository callRepository;

	@Autowired
	private CallResource callResource;

	@Autowired
	private AMQPGateway aMQPGateway;

	@Scheduled(fixedRate = 1000 * 3600)
	public void reportCurrentTime() {
		Call latestCall = callRepository.findTopByOrderByDatetimeDesc();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String latestCallDatetimeFormatted = latestCall.getDatetime().format(formatter);
		log.info("Service: {}. Latest 911 Call Formatted Datetime is {}", serviceName, latestCallDatetimeFormatted);

		List<Call> callsList = callResource.calls("datetime > '" + latestCallDatetimeFormatted + "'");
		if (callsList.size() == 0) {
			log.info("Service: {}. No new 911 calls", serviceName);
			return;
		}

		for (Call call : callsList) {
			Call existingCall = callRepository.findByIncidentNumber(call.getIncidentNumber());
			if (existingCall == null) {
				log.info("Service: {}. Incident: {}. Fetched Call Object {}", serviceName, call.getIncidentNumber(), call);
				call = callRepository.save(call);
				aMQPGateway.generate(call);
			} else {
				// sometimes Open Data Service returns same incident twice
				log.info("Service: {}. Incident: {}. Fetched Duplicate Call Object {}", serviceName, existingCall.getIncidentNumber(), call);
				existingCall.setAddress(call.getAddress());
				existingCall.setLatitude(call.getLatitude());
				existingCall.setLongitude(call.getLongitude());
				existingCall.setType(call.getType());
				callRepository.save(existingCall);
				// we don't want to send existing call details again
			}
		}

		/*
		 * Call call = callRepository.findOne(50L); aMQPGateway.generate(call);
		 */
	}
}
