package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.entity.Call;

@Component
public class CallResourceImpl implements CallResource {

	@Value("${seattleopendata.token}")
	public String token;
	
	@Override
	public List<Call> calls() {

		return null;
	}

}
