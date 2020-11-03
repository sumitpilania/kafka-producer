package com.exercise.producerapp.controller;

import com.exercise.producerapp.KafkaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    private static final Logger logger =LoggerFactory.getLogger(KafkaController.class.getName());
    private KafkaTemplate<String, String> template;
    public KafkaController(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    @PostMapping("/kafka/produce")
    public void produce(@RequestBody KafkaMessage message) {
        logger.info("Producer got the message : "+message.toString());
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        try {
            String stringMessage = objectWriter.writeValueAsString(message);
            logger.info("Sending message : "+stringMessage);
            template.send("myTopic", stringMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
