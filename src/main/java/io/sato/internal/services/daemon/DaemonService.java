package io.sato.internal.services.daemon;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

public class DaemonService extends AbstractService {
    public DaemonService() {
        super(new HashSet<>());
    }
}
