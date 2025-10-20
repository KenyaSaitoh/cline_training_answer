package dev.cline.apricot.rs.greeting;

import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/greeting")
public class GreetingResource {

    @GET
    @Path("/hello/{personName}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(@PathParam("personName") String personName) {
        return "Hello " + personName + "!";
    }

    @GET
    @Path("/goodbye/{personName}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayGoodbye(@PathParam("personName") String personName) {
        return "Goodbye " + personName + "!";
    }

    @GET
    @Path("/morning")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayGoodMorning(@QueryParam("personName") String personName) {
        return "Good Morning " + personName + "!";
    }

    @POST
    @Path("/afternoon")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayGoodAfternoon(@FormParam("personName") String personName) {
        return "Good Afternoon " + personName + "!";
    }

    @GET
    @Path("/night")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayGoodNight(@HeaderParam("personName") String personName) {
        return "Good Night " + personName + "!";
    }

    @GET
    @Path("/evening")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayGoodEvening(@CookieParam("personName") String personName) {
        return "Good Evening " + personName + "!";
    }
}