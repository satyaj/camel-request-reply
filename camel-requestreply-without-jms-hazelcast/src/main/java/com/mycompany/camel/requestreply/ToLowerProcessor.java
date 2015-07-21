package com.mycompany.camel.requestreply;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class ToLowerProcessor implements  AsyncProcessor {
	
	@Override
	public void process(Exchange exchange) throws Exception {}

	@Override
	public boolean process(Exchange exchange, AsyncCallback callback)  {
		
		LowerCaseWorker lcw = new LowerCaseWorker(exchange, callback);
		
		//do something else
		
		
		return false;

	}

}
