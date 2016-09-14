package io.sato.service.webserver;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

/**
 * Expected functionality:
 * list services and plugins
 * status, start, stop, restart (sato services)
 */
public class WebServerService extends AbstractService {
    public WebServerService() {
        super(new HashSet<>());
    }
}
