package com.mycompany.camel.requestreply;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Message;

public class LowerCaseWorker extends Thread {
	public LowerCaseWorker (Exchange exchange, AsyncCallback callback) {

		// get the input message
		Message in = exchange.getIn();
		
		// get the body payload
		String payload = in.getBody(String.class);
		
		// convert to lower case
		payload = payload.toLowerCase();
		
		// update the in payload
		in.setBody(payload);

		callback.done(false);
		
	}
}
