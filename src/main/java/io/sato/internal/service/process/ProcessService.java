package io.sato.internal.service.process;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

/**
 * Expected functionality:
 * list processes,
 * info, find, send signal
 */
public class ProcessService extends AbstractService {
    public ProcessService() {
        super(new HashSet<>());
    }
}
