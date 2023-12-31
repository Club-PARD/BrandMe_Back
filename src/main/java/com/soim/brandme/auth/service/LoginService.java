package com.soim.brandme.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@PropertySource("classpath:application.properties")
@Slf4j
@Service
public class LoginService {

    private final Environment env;
    private final WebClient webClient;

    public LoginService(Environment env, WebClient.Builder webClientBuilder) {
        this.env = env;
        this.webClient = webClientBuilder.build();
    }

    public void socialLogin(String code) {
        String registrationId = "google";
        try {
            String accessToken = getAccessToken(code, registrationId);

            getUserResource(accessToken, registrationId).subscribe(userResourceNode -> {

                try {
                    String id = userResourceNode.get("id").asText();
                    String email = userResourceNode.get("email").asText();
                    String nickname = userResourceNode.get("name").asText();

                } catch (Exception e) {
                    log.error("Error processing user resource node", e);
                }
            }, error -> {
                log.error("getUserResource에서 에러 발생", error);
            });
        } catch (Exception e) {
            log.error("Google login 실패: {}", e.getMessage());
        }
    }


    private String getAccessToken(String authorizationCode, String registrationId) {
        String clientId = env.getProperty("oauth2." + registrationId + ".client-id");
        String clientSecret = env.getProperty("oauth2." + registrationId + ".client-secret");
        String redirectUri = env.getProperty("oauth2." + registrationId + ".redirect-uri");
        String tokenUri = "https://oauth2.googleapis.com/token";

        String accessToken = webClient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("code", authorizationCode)
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("redirect_uri", redirectUri)
                        .with("grant_type", "authorization_code"))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> jsonNode.get("access_token").asText())
                .block();

        return accessToken;
    }

    private Mono<JsonNode> getUserResource(String accessToken, String registrationId) {
        String resourceUri = "https://www.googleapis.com/oauth2/v2/userinfo";

        return webClient.get()
                .uri(resourceUri)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(JsonNode.class);
    }

    public void googleLogin() {
        log.info("Google login attempt================================");

    }
}
