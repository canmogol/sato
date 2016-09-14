package io.sato.service.metrics;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

public class MetricsService extends AbstractService {
    public MetricsService() {
        super(new HashSet<>());
    }
}
