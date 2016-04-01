package io.sato.internal.services.metrics;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

public class MetricsService extends AbstractService {
    public MetricsService() {
        super(new HashSet<>());
    }
}
