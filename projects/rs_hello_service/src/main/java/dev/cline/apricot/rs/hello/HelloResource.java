package dev.cline.apricot.rs.hello;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello")
public class HelloResource {

    @GET
    @Path("/jaxrs")
    @Produces("text/plain")
    public String sayHello() {
        return "Hello JAX-RS!";
    }
}