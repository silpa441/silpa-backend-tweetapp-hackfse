package com.tweetapp.api.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService 
{
	public static final String topic = "tweetRunMessage";
	
	@Autowired
	private KafkaTemplate<String,String> kafkaTemp;
	
	public void sendMessage(String message)
	{
		System.out.println("Publishing to topic"+ topic);
		this.kafkaTemp.send(topic,message);
	}
}