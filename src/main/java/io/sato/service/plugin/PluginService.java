package io.sato.service.plugin;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

/**
 * Expected functionality:
 * list, find, install, update, remove available and installed plugins
 * create, read, edit plugins
 * status and description of a plugin
 * enable, disable plugins
 */
public class PluginService extends AbstractService {
    public PluginService() {
        super(new HashSet<>());
    }
}
