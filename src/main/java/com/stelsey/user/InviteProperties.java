package com.stelsey.user;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.invites")
public record InviteProperties(String baseUrl, Duration ttl) {

}
