package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import com.example.entity.Call;

@ComponentScan
@MessagingGateway
public interface AMQPGateway {

	@Gateway(requestChannel = "output")
	void generate(Call call);
	//void generate(String call);

}