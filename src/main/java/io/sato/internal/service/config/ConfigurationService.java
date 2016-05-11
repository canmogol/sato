package io.sato.internal.service.config;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

/**
 * Expected functionality:
 * list available configurations, etc, db, daemons config files etc.
 * provide default configurations for applications/daemons
 */
public class ConfigurationService extends AbstractService {
    public ConfigurationService() {
        super(new HashSet<>());
    }
}
