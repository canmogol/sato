package io.sato.internal.service.metrics;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

/**
 * Expected functionality:
 * CPU, GPU, Disk(IO, Node, Size, utilization etc.), Process/Thread, Devices, Kernel etc.
 * tree representation of a process/thread
 * timeline of values
 */
public class MetricsService extends AbstractService {
    public MetricsService() {
        super(new HashSet<>());
    }
}
