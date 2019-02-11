package com.zenika.zencontact.resource.auth;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AuthenticationService {

    private static AuthenticationService INSTANCE = new AuthenticationService();

    public static AuthenticationService getInstance() { return INSTANCE; }

    private UserService userService = UserServiceFactory.getUserService();

    public String getLoginUrl(String url) {
        return userService.createLoginURL(url);
    }

    public String getLogoutUrl(String url) {
        return userService.createLogoutURL(url);
    }

    public boolean isAdmin() {
        return userService.isUserAdmin();
    }

    public User getUser() {
        return userService.getCurrentUser();
    }

    public String getUsername() {
        return getUser().getNickname();
    }

}
