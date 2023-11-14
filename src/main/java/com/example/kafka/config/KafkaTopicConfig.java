package com.example.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {

	@Autowired
	private PropertyConfig propertyConfig;

	@Bean
	public KafkaAdmin kafkaAdmin() {
		return new KafkaAdmin(propertyConfig.getKafkaConfig());
	}

	@Bean
	public NewTopic topic1() {
		return new NewTopic(propertyConfig.getKafkaTopic(), 4, (short) 1);
	}

}