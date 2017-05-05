package com.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Call;
import com.example.service.CallResource;


@RestController
@RequestMapping("/api/reddit")
public class Cont {

	//@Autowired
	//private TestService2 test;
	

    @Autowired
    private CallResource callResource;

    @RequestMapping(method = RequestMethod.GET)
    public  List<Call> get() {
        return callResource.calls();
    }

}
