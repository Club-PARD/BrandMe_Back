package com.soim.brandme.auth.domain;

public interface OAuth2UserInfo {
    String getEmail();
    String getName();
    String getProviderId();
    String getProvider();

    String getImage();
    String locale();
}
