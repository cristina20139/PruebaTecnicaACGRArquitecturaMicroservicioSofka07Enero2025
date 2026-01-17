package com.sofka.microservicios.cuentas.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;


/**
 * Configuración de Kafka para la aplicación de cuentas y movimientos.
 * Define los beans necesarios para la integración con Kafka, incluyendo
 * la fábrica de consumidores y la creación de tópicos.
 * 
 */

@Configuration
@EnableKafka
public class KafkaConfig {

   
    /**
     *  
     * @param properties
     * @return
     */

    @Bean
    public ConsumerFactory<String, Object> consumerFactory(KafkaProperties properties) {
        Map<String, Object> props = properties.buildConsumerProperties(null);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * 
     * @return
     */

    @Bean
    public NewTopic clientesTopic() {
        return TopicBuilder.name("clientes.eventos").partitions(1).replicas(1).build();
    }
}
