package io.sato.service.boot.log;

import com.fererlab.dispatch.log.BaseLogger;

public class BootServiceLogger extends BaseLogger {

    public void willHandleRebootEvent(String event) {
        log("RebootEvent: " + event);
    }

    public void willHandleHaltEvent(String event) {
        log("HaltEvent: " + event);
    }
}
