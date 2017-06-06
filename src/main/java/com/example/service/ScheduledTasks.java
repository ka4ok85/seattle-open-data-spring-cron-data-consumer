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
    	/*
        Call latestCall = callRepository.findTopByOrderByDatetimeDesc();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String latestCallDatetimeFormatted = latestCall.getDatetime().format(formatter);
        log.info("Latest 911 Call Formatted Datetime is {}", latestCallDatetimeFormatted);
        
        List<Call> x = callResource.calls("datetime > '" + latestCallDatetimeFormatted + "'");
        for (Call call : x) {
        	log.info("Fetched Call Object {}", call);
			callRepository.save(call);
		}
		*/
 	   Call call = new Call();
 	   call.setIncidentNumber("test Incident Number");
 	   call.setType("test type");
 	   call.setAddress("test address");
 	   aMQPGateway.generate(call);
       
       
       //aMQPGateway.generate(call.toString());
    }
}
