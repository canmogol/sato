package io.sato.logging;

import com.fererlab.dispatch.log.DefaultLogging;
import com.fererlab.dispatch.log.Logging;

public abstract class SatoLogger implements Logging {

    private Logging logging;

    public SatoLogger(Class<?> owner) {
	logging = new DefaultLogging(owner, new SatoLogFormatter());
    }

    public void log(String log) {
	logging.log(log);
    }

    @Override
    public void error(String log) {
	logging.error(log);
    }

}
