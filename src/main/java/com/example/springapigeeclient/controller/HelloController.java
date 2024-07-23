package com.example.springapigeeclient.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.springapigeeclient.dto.HelloWorld;
import com.example.springapigeeclient.dto.RequestData;

import net.minidev.json.JSONObject;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {
    
	private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${api.url}")
    private String clientUrl; // --> URL that needs to be used but authenticates only with APIGEE

    @Autowired
    private WebClient webClient;


    @GetMapping("/hello")
    public Mono<HelloWorld> hello() {
    	logger.info("Entered inside HelloController:hello() method ");
    	
    	JSONObject obj = new JSONObject();
    	obj.put("input", "<Input data for the CLIENT-URL>");
    	
        return webClient.post().uri(clientUrl)
        		.body(Mono.just(obj), RequestData.class)
        		.retrieve().bodyToMono(HelloWorld.class);

    }

}
