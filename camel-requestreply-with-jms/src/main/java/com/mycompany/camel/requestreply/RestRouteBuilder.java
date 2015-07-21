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
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hazelcast.HazelcastConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;

public class RestRouteBuilder extends RouteBuilder {

   
   public void configure() throws Exception {
	   String uri = "direct:";
	
	   // This is the route for the reply-queue
        from("activemq:queue:data-queue?asyncConsumer=true")
        .setHeader("MyID", xpath("person/@user"))
        .log("Header 'MyID' is : ${header.MyID}" )
        .processRef("toLower")
        .to("file:src/data/out?noop=true")
        .to("consumerProcessor")
        .transform(body());
       	

        
        from("hazelcast:map:foo")
        .choice()
        	.when(header(HazelcastConstants.LISTENER_ACTION).isEqualTo(HazelcastConstants.ADDED))
        			.setExchangePattern(ExchangePattern.InOut)
        			.recipientList(simple("seda:${header.CamelHazelcastObjectId}"))
        			.setHeader(HazelcastConstants.OPERATION, constant(HazelcastConstants.DELETE_OPERATION))
        			.to("hazelcast:map:foo");
   

   }
}


