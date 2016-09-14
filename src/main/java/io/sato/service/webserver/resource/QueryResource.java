package io.sato.service.webserver.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/client")
@Consumes("*/*")
@Produces({MediaType.APPLICATION_JSON})
public class QueryResource {

    @GET
    @Path("/test")
    public String test() {
        return "test " + (new Random().nextDouble());
    }

}
