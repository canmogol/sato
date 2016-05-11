package io.sato.internal.service.packaging;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

/**
 * Expected functionality:
 * list installed packages
 * install, update, remove, info (tree representation of packages and their contents)
 */
public class PackagingService extends AbstractService {
    public PackagingService() {
        super(new HashSet<>());
    }
}
