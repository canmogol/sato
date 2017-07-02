package io.sato.metrics;

import io.sato.dbc.annotation.Contracted;
import io.sato.dbc.annotation.Ensures;
import io.sato.dbc.annotation.Requires;
import io.sato.dbc.interceptor.DBCInterceptor;
import io.sato.property.Property;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/metrics")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Api(value = "/metrics", description = "application metrics")
@Interceptors({DBCInterceptor.class})
@Contracted
public class MetricsController {

    @Inject
    private MetricsAggregate metricsAggregate;

    @Inject
    @Property
    private String version;

    @Inject
    @Property("application.name")
    private String applicationName;

    @GET
    @Path("{key}")
    @ApiOperation(
        value = "{key}",
        notes = "Returns the given key's value in metrics." +
            "<br/>" +
            "If key is null or empty string, returns a 400 bad request with error message." +
            "<br/>" +
            "<img src=\"http://yuml.me/2cdba121\" alt=\"yuml.me/2cdba121\">"
    )
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Value found for this key", response = Object.class),
            @ApiResponse(code = 400, message = "Invalid key supplied, either null or an empty string"),
            @ApiResponse(code = 404, message = "Value not found for this key in metrics")
        }
    )
    @Requires({
        "params[0]!=null",
        "params[0]!=\"\""
    })
    @Ensures({
        "result.getHeaders().containsKey(\"X-SATO-Version\")",
        "result.getHeaders().containsKey(\"X-SATO-ApplicationName\")"
    })
    public Response getMetrics(@PathParam("key") String key) {
        return metricsAggregate.get(key)
            .notEmpty(Response::ok, Response.status(Response.Status.NOT_FOUND   ))
            .header("X-SATO-Version", version)
            .header("X-SATO-ApplicationName", applicationName)
            .build();
    }

}
