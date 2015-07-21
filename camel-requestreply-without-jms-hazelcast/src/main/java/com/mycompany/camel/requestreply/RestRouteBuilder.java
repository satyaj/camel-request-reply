package com.mycompany.camel.requestreply;
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class RestRouteBuilder extends RouteBuilder {
   @Override
   public void configure() throws Exception {
	   String uri = "direct:";
  
	   // This is the main route
	   
	   from("cxfrs:http://0.0.0.0:9190?resourceClasses=com.mycompany.camel.requestreply.InputDataEndpoint")
	   .log("Body before the call : ${body}")
        .setHeader("ObjectId", xpath("person/@user"))
        .log("Header 'ObjectId' is : ${header.ObjectId}" )
        .processRef("toLower")
        .to("file:src/data/out")
        .log("After file write: ${headers}")
        .to("consumerProcessor")
        .transform(body());
 
	   // This is the route from File to Seda queue
	   from("file:src/data/out?moveFailed=error")
	   .marshal().string("UTF-8")
	   .log("Body after the file polling is: ${body}")
		//.setExchangePattern(ExchangePattern.InOut)
		.setHeader("ObjectId", xpath("person/@user"))
		.recipientList(simple("seda:${header.ObjectId}?size=1"))
		.log("Completed processing");

   }
}
