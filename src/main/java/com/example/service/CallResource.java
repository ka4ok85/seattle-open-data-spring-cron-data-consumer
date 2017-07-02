package com.example.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.config.FeignConfiguration;
import com.example.entity.*;

@FeignClient(name = "911-calls-service", url = "https://data.seattle.gov/resource", configuration = FeignConfiguration.class)
public interface CallResource {

	@RequestMapping(method = RequestMethod.GET, value = "/fire-911.json?$limit=50&$order=datetime desc&$select=datetime,address,type,incident_number,latitude,longitude", headers = "X-App-Token=${seattleopendata.token}")
	public List<Call> calls(@RequestParam("$where") String where);

}
