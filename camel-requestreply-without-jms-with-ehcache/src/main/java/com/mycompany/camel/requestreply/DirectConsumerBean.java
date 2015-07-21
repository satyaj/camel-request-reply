package com.mycompany.camel.requestreply;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cache.CacheConstants;
import org.apache.camel.component.cache.CacheEventListenerRegistry;
import org.apache.camel.util.AsyncProcessorHelper;
import org.springframework.beans.factory.annotation.Autowired;

public class DirectConsumerBean implements AsyncProcessor {
	
	private ConsumerTemplate consumer;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    @Override
    public boolean process(final Exchange exchange,
                           final AsyncCallback asyncCallback) {		

		
		final String id = exchange.getIn().getHeader("ObjectId", String.class);
		consumer = exchange.getContext().createConsumerTemplate();
		final String uri = "cache://replyCache";
		System.out.println("URI is : " + uri);
		
		exchange.getIn().setHeader(CacheConstants.CACHE_OPERATION, CacheConstants.CACHE_OPERATION_GET);
		exchange.getIn().setHeader(CacheConstants.CACHE_KEY, id);

		
        executorService.submit(new Runnable() {
            @Override
            public void run() {
            	String msg = consumer.receiveBody(uri, 3000, String.class);        		

         		exchange.getIn().setBody(msg);
        		
        		System.out.println("Processed: " + msg);
        		asyncCallback.done(false);
            }
        });
        return false;
    }


	@Override
	public void process(Exchange exchange) throws Exception {
    	AsyncProcessorHelper.process(this, exchange);		
	}
}
