package com.example.kafka.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.kafka.config.PropertyConfig;

@Component
public class KafkaReceiver {

	@KafkaListener(topics = PropertyConfig.KAFKA_TOPIC)
	public void listenWithHeaders(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
		System.out.println("Received Message: " + message + " from partition: " + partition);
	}

}
