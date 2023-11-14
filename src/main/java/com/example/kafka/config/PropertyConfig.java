package com.example.kafka.config;

import java.util.Map;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.Data;

@Configuration
@Data
public class PropertyConfig {

	public final static String KAFKA_TOPIC = "${kafka.topic}";

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	private final Map<String, String> kafkaConfigParamsMap = Map.of(AdminClientConfig.CLIENT_ID_CONFIG,
			"spring.kafka.client-id", SaslConfigs.SASL_JAAS_CONFIG, "spring.kafka.properties.sasl.jaas.config",
			SaslConfigs.SASL_MECHANISM, "spring.kafka.properties.sasl.mechanism",
			AdminClientConfig.SECURITY_PROTOCOL_CONFIG, "spring.kafka.properties.security.protocol");

	private Map<String, Object> kafkaConfig;

	@Value(value = KAFKA_TOPIC)
	private String kafkaTopic;

	public PropertyConfig(Environment env) {
		kafkaConfig = kafkaConfigParamsMap.entrySet().stream().filter(e -> {
			String propertyValue = env.getProperty(e.getValue());
			return propertyValue != null && !propertyValue.isBlank();
		}).collect(Collectors.toMap(e -> e.getKey(), e -> env.getProperty(e.getValue())));
	}

	public String getBootstrapAddress() {
		return bootstrapAddress;
	}

	public String getKafkaTopic() {
		return kafkaTopic;
	}

	public Map<String, Object> getKafkaConfig() {
		kafkaConfig.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return kafkaConfig;
	}

}
