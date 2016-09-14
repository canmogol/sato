package io.sato.internal.command;

import com.fererlab.dispatch.log.BaseLogger;

public class CommandLogger extends BaseLogger {

    public void runtimeExecError(String commandName, String errorMessage) {
        error("got exception at " + commandName + " error: " + errorMessage);
    }

}
