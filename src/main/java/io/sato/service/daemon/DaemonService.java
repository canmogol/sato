package io.sato.service.daemon;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

/**
 * Expected functionality:
 * list daemons,
 * operations on a daemon; status, start, stop, restart, reload
 */
public class DaemonService extends AbstractService {
    public DaemonService() {
        super(new HashSet<>());
    }
}
