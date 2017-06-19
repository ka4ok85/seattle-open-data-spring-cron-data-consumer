package com.example.service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.entity.Call;
import com.example.repository.CallRepository;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    @Autowired
    private CallRepository callRepository;
    
    @Autowired
    private CallResource callResource;
    
    @Autowired
    private AMQPGateway aMQPGateway;

    @Scheduled(fixedRate = 1000*3600)
    public void reportCurrentTime() {

    	Call latestCall = callRepository.findTopByOrderByDatetimeDesc();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String latestCallDatetimeFormatted = latestCall.getDatetime().format(formatter);
        log.info("Latest 911 Call Formatted Datetime is {}", latestCallDatetimeFormatted);
        
        List<Call> callsList = callResource.calls("datetime > '" + latestCallDatetimeFormatted + "'");
        for (Call call : callsList) {
        	Call existingCall = callRepository.findByIncidentNumber(call.getIncidentNumber());
        	if (existingCall == null) {
            	log.info("Service: {}. Incident: {}. Fetched Call Object {}", "911-calls-cron-consumer", call.getIncidentNumber(), call);
            	call = callRepository.save(call);
            	aMQPGateway.generate(call);        		
        	} else {
        		// sometimes Open Data Service updates incident with updated details, but incident number is reserved initially
        		log.info("Service: {}. Incident: {}. Fetched Duplicate Call Object {}", "911-calls-cron-consumer", existingCall.getIncidentNumber(), call);
        		log.info("Service: {}. Incident: {}. Stored Duplicate Call Object {}", "911-calls-cron-consumer", existingCall.getIncidentNumber(), existingCall);
				existingCall.setAddress(call.getAddress());
				existingCall.setLatitude(call.getLatitude());
				existingCall.setLongitude(call.getLongitude());
				existingCall.setType(call.getType());
				log.info("Service: {}. Incident: {}. Updated Call Object {}", "911-calls-cron-consumer", existingCall.getIncidentNumber(), existingCall);
				callRepository.save(existingCall);
        		// we don't want to send again existing call details
        	}
		}
    	
/*
 	   Call call = callRepository.findOne(50L);
 	   //aMQPGateway.generate(call);

*/ 	   
    }
}
