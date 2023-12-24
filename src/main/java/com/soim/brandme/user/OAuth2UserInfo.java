package com.soim.brandme.user;

public interface OAuth2UserInfo {
    String getEmail();
    String getName();
    String getProviderId();
    String getProvider();

    String getImage();
    String locale();
}
