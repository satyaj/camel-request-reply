package com.mycompany.camel.requestreply;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;

public class DirectConsumerBean implements Processor {
	
	private ConsumerTemplate consumer;

	public void process(Exchange exchange)
	{
		
		String id = exchange.getIn().getHeader("ObjectId", String.class);
		consumer = exchange.getContext().createConsumerTemplate();
		String uri = "seda:" + id + "?size=1";
		System.out.println("URI is : " + uri);
		
		System.out.println("ConsumerTemplate is : " + consumer);
		String msg = consumer.receiveBody(uri,5000, String.class);
		
		System.out.println("Processed:  " + msg);
		exchange.getIn().setBody(msg);
		
		try {
			exchange.getContext().removeEndpoints(uri);
			System.out.println("removed endpoint: " + uri);
		} catch (Exception ex) {
			System.out.println("Endpoint removal exception");
		}
    }
}
