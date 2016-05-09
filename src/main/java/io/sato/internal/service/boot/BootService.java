package io.sato.internal.service.boot;

import com.fererlab.dispatch.event.Event;
import com.fererlab.dispatch.event.ShutdownEvent;
import com.fererlab.dispatch.service.AbstractService;
import io.sato.internal.command.Command;
import io.sato.internal.command.Invoker;
import io.sato.internal.service.boot.event.HaltEvent;
import io.sato.internal.service.boot.event.RebootEvent;
import io.sato.internal.service.boot.factory.BootCommandFactory;
import io.sato.internal.service.boot.log.BootServiceLogger;

import java.util.HashSet;

public class BootService extends AbstractService {

    private BootServiceLogger logger = new BootServiceLogger();

    public BootService() {
        super(new HashSet<Class<? extends Event>>() {{
            add(RebootEvent.class);
            add(HaltEvent.class);
        }});
    }

    public void handleEvent(RebootEvent event) {
        logger.willHandleRebootEvent(event.toString());
        Command command = BootCommandFactory.getBootCommandFactory().getRebootCommand();
        Invoker invoker = new Invoker();
        invoker.execute(command);
        broadcast(new ShutdownEvent());
    }

    public void handleEvent(HaltEvent event) {
        logger.willHandleHaltEvent(event.toString());
        Command command = BootCommandFactory.getBootCommandFactory().getShutdownCommand();
        Invoker invoker = new Invoker();
        invoker.execute(command);
        broadcast(new ShutdownEvent());
    }

}
