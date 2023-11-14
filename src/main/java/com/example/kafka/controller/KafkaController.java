package com.example.kafka.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafka.config.PropertyConfig;

@RestController
public class KafkaController {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private PropertyConfig propertyConfig;

	@PostMapping("/kafka")
	public String postToKafka(@RequestBody String msg) {
		CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(propertyConfig.getKafkaTopic(), msg);
		try {
			SendResult<String, String> result = future.get();
			System.out.println("Sent message=[" + msg + "] with offset=[" + result.getRecordMetadata().offset() + "]");
		} catch (Exception e) {
			System.out.println("Unable to send message=[" + msg + "] due to : " + e.getMessage());
			throw new RuntimeException(e);
		}
		return "done";
	}
}
