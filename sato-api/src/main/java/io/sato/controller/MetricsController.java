package io.sato.controller;

import io.sato.dbc.annotation.Contracted;
import io.sato.dbc.annotation.Ensures;
import io.sato.dbc.annotation.Requires;
import io.sato.exception.MetricsException;
import io.sato.metrics.MetricsAggregate;
import io.sato.rest.response.Response;
import io.sato.types.Maybe;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Contracted
@RestController
@RequestMapping(value = "/metrics", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class MetricsController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MetricsController.class);

    private static final String VALUE_NOT_FOUND = "VALUE_NOT_FOUND";

    @Autowired
    Tracer tracer;

    @Autowired
    private MetricsAggregate metricsAggregate;

    @Value("${metrics.version}")
    private String version;

    @Value("${metrics.applicationName}")
    private String applicationName;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(
        value = "",
        notes = "Returns all the metrics."
    )
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Returns all the metrics", response = Object.class)
        }
    )
    public
    @ResponseBody
    Response<Map<String, Object>> getMetrics() throws MetricsException {
        // get trace id
        long traceId = tracer.getCurrentSpan().getTraceId();
        // create response builder
        final Response.Builder<Map<String, Object>> builder = new Response.Builder<Map<String, Object>>().traceId(traceId);
        // log start message
        logger.debug("getMetrics method start", traceId);

        // get all metrics
        Map<String, Object> metrics = metricsAggregate.getMetrics();

        // return response
        return builder
            .data(metrics)
            .message("all available metrics")
            .status(HttpStatus.OK.value())
            .build();
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
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
            @ApiResponse(code = 200, message = "Value found for this key", response = String.class),
            @ApiResponse(code = 400, message = "Invalid key supplied, either null or an empty string"),
            @ApiResponse(code = 404, message = "Value not found for this key in metrics")
        }
    )
    public Response<String> getMetrics(
        @ApiParam(value = "ex: version")
        @PathVariable String key
    ) {
        // get trace id
        final long traceId = tracer.getCurrentSpan().getTraceId();
        // create response builder
        final Response.Builder<String> builder = new Response.Builder<String>().traceId(traceId);

        // log start message
        String startMessage = String.format("getMetrics method start for key: %1s", key);
        logger.debug(startMessage, traceId);

        // get metrics value
        Maybe<Object> valueMaybe = metricsAggregate.get(key);

        // handle value
        valueMaybe.notEmpty(value -> {
            String valueString = String.valueOf(value);
            String returnMessage = String.format("getMetrics method returned for key: %1s value: %2s", key, valueString);
            logger.debug(returnMessage, traceId);
            builder
                .data(valueString)
                .message(returnMessage)
                .status(HttpStatus.OK.value());
        }, () -> {
            String returnMessage = String.format("getMetrics method could not find value for key: %1s ", key);
            logger.debug(returnMessage, traceId);
            builder
                .data(VALUE_NOT_FOUND)
                .message(returnMessage)
                .status(HttpStatus.NOT_FOUND.value());
        });

        // return response
        return builder.build();
    }

}
