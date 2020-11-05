package com.exercise.producerapp;

public class KafkaMessage {
    String message;

    public KafkaMessage(String str) {
        message = str;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "KafkaMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
