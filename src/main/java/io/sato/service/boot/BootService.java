package io.sato.service.boot;

import com.fererlab.dispatch.event.Event;
import com.fererlab.dispatch.service.AbstractService;
import io.sato.service.boot.event.HaltEvent;
import io.sato.service.boot.event.RebootEvent;
import io.sato.service.boot.log.BootServiceLogger;

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
    }

    public void handleEvent(HaltEvent event) {
        logger.willHandleHaltEvent(event.toString());
    }

}
