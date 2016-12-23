package io.sato.service.usergroup;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

/**
 * Expected functionality:
 * list, add, remove,
 * edit users, list/add/remove; groups, permissions, update credentials/names/home directory etc.
 */
public class UserService extends AbstractService {
    public UserService() {
        super(new HashSet<>());
    }
}
