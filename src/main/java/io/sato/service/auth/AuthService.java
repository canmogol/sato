package io.sato.service.auth;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

/**
 * Expected functionality:
 * provide authentication and authorization of sato users
 * integration with active directory, ldap, database and local user providers etc.
 * auditing of actions: login, logout, sato service and RWService etc.
 */
public class AuthService extends AbstractService {
    public AuthService() {
        super(new HashSet<>());
    }
}
