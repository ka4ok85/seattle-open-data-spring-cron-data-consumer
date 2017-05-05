package com.example.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.*;

import feign.Feign;



public class CallDataCallerService {
	
	//@RequestMapping(method = RequestMethod.GET, value = "/java.json")
    //List<Call> calls();

	/*
	
	CallResource github = Feign.builder()
			.decoder(new )
			
			
            .decoder(new GsonDecoder())
            .target(CallResource.class, "https://api.github.com");
*/
	/*
// Fetch and print a list of the contributors to this library.
List<Call> contributors = github.contributors("OpenFeign", "feign");
for (Call contributor : contributors) {
	System.out.println(contributor.getIncidentNumber());
}
*/
}
