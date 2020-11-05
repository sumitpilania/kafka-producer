package com.exercise.producerapp;

import com.exercise.producerapp.controller.KafkaController;
import com.exercise.producerapp.models.DataSchema;
import com.exercise.producerapp.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import static java.lang.Math.round;

@SpringBootApplication
public class ProducerAppApplication implements CommandLineRunner{

	@Autowired
	private KafkaTemplate<String, String> template;

	private static final Logger logger = LoggerFactory.getLogger(ProducerAppApplication.class.getName());
	public static void main(String[] args) {
		SpringApplication.run(ProducerAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Producer Application Started Producing !!");

		while(true){

			Random random = new Random();
			String str = Integer.toString(random.nextInt());
			KafkaMessage message = new KafkaMessage(str);

			DataSchema dataSchema = Utils.getNextDataSchema();

			Thread.sleep(5000);
			ObjectMapper mapper = new ObjectMapper();
			ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
			try {
				//String stringMessage = objectWriter.writeValueAsString(message);
				String stringMessage = objectWriter.writeValueAsString(dataSchema);
				logger.info("Sending message : "+stringMessage);
				template.send("myTopic", stringMessage);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}




}
