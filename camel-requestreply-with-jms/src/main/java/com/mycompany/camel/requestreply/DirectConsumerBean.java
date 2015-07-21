package com.mycompany.camel.requestreply;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;

public class DirectConsumerBean implements Processor {
	
	private ConsumerTemplate consumer;

	public void process(Exchange exchange)
	{
		
		String id = exchange.getIn().getHeader("MyID", String.class);
		consumer = exchange.getContext().createConsumerTemplate();
		String uri = "seda:" + id;
		System.out.println("URI is : " + uri + "?waitForTaskToComplete=Always");
		
		System.out.println("ConsumerTemplate is : " + consumer);
		String msg = consumer.receiveBody(uri,5000, String.class);
		

		exchange.getIn().setBody(msg);
	
    }
}
