package io.sato.internal.services.webserver;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

public class WebServerService extends AbstractService {
    public WebServerService() {
        super(new HashSet<>());
    }
}
