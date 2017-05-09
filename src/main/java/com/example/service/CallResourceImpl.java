package com.example.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Call;

@Component
public class CallResourceImpl implements CallResource {

	@Override
	public List<Call> calls(@RequestParam("$where") String where) {
		return null;
	}

}
