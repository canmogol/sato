package io.sato.service.boot;

import com.fererlab.dispatch.event.Event;
import com.fererlab.dispatch.event.ShutdownEvent;
import com.fererlab.dispatch.service.AbstractService;
import io.sato.command.Command;
import io.sato.command.Invoker;
import io.sato.service.boot.factory.BootCommandFactory;
import io.sato.service.boot.event.HaltEvent;
import io.sato.service.boot.event.RebootEvent;
import io.sato.service.boot.log.BootServiceLogger;

import java.util.HashSet;

/**
 * Expected functionality:
 * reboot and halt the system
 */
public class BootService extends AbstractService {

    private BootServiceLogger logger = new BootServiceLogger(getClass());
    private BootCommandFactory factory = BootCommandFactory.getBootCommandFactory();

    public BootService() {
	super(new HashSet<Class<? extends Event>>() {{
	    add(RebootEvent.class);
	    add(HaltEvent.class);
	}});
    }

    public void handleEvent(RebootEvent event) {
	logger.willHandleRebootEvent(event.toString());
	Command command = factory.getRebootCommand();
	Invoker invoker = new Invoker();
	invoker.execute(command);
	broadcast(new ShutdownEvent());
    }

    public void handleEvent(HaltEvent event) {
	logger.willHandleHaltEvent(event.toString());
	Command command = factory.getShutdownCommand();
	Invoker invoker = new Invoker();
	invoker.execute(command);
	broadcast(new ShutdownEvent());
    }

}
