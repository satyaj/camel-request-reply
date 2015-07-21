package com.mycompany.camel.requestreply;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;



@Path("/")
public class InputDataEndpoint {
	
	public InputDataEndpoint() {
		
	}
	
	@POST
	@Path("/test")
	@Consumes("application/xml")
	@Produces("application/xml")
	public String getMessage(String data) {
		System.out.println("Called the frontend Service : " + data);
		return "";
	}
}
