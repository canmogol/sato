package io.sato.service.usergroup;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

/**
 * Expected functionality:
 * list, add, remove, edit groups
 */
public class GroupService extends AbstractService {
    public GroupService() {
        super(new HashSet<>());
    }
}
