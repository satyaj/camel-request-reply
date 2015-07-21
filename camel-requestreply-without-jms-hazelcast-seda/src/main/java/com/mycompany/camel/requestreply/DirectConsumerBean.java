package com.mycompany.camel.requestreply;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.util.AsyncProcessorHelper;
import org.springframework.beans.factory.annotation.Autowired;

public class DirectConsumerBean implements AsyncProcessor {
	
	private ConsumerTemplate consumer;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void process(Exchange exchange) throws Exception {
       // throw new IllegalStateException("Should never be called");
    	AsyncProcessorHelper.process(this, exchange);
    }

    @Override
    public boolean process(final Exchange exchange,
                           final AsyncCallback asyncCallback) {		

		//do something else
   		final String id = exchange.getIn().getHeader("ObjectId", String.class);
		final String uri = "direct:" + id;
		System.out.println("URI is : " + uri);
		consumer = exchange.getContext().createConsumerTemplate();
		System.out.println("ConsumerTemplate is : " + consumer);



        executorService.submit(new Runnable() {
            @Override
            public void run() {
            	
            	String msg = consumer.receiveBody(uri, String.class);        		

         		exchange.getIn().setBody(msg);
        		
        		System.out.println("Processed: " + msg);
        		asyncCallback.done(false);
            }
        });
        return false;


	}

}
