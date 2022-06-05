package org.five.sonarqubot.events;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.five.sonarqubot.client.ProjectResponse;
import org.five.sonarqubot.client.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@PropertySource("classpath:secret.properties")
public class WebClientServiceImpl implements WebClientService {
    @Value("${user}")
    private String user="admin";
    @Value("${password}")
    private String password="isa";
    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientServiceImpl.class);

    WebClient client = WebClient.builder()
            .baseUrl("http://localhost:9000/api")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .filter(ExchangeFilterFunctions
                    .basicAuthentication(user, password))
            .build();

    private final String uuid= String.valueOf(UUID.randomUUID());

    public String base64Creds() {
        String plainCreds = user + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        return new String(base64CredsBytes);
    }
    public String randomString() {
        return RandomStringUtils.randomAlphabetic(3);
    }

    @Override

    public Mono<TokenResponse> createToken () {

        LinkedMultiValueMap<String, String> tokenData = new LinkedMultiValueMap<>();
        tokenData.add("name", randomString());


        return client.post()
                .uri("/user_tokens/generate")
                .bodyValue(tokenData)
                .retrieve()
                .bodyToMono(TokenResponse.class);
    }


    @Override
    public Mono<ProjectResponse> createProject () {
        LinkedMultiValueMap<String, String> projectData = new LinkedMultiValueMap<>();
        projectData.add("name", uuid);
        projectData.add("project", uuid);

        return client.post()
                .uri("/projects/create").bodyValue(projectData)
                .retrieve()
                .bodyToMono(ProjectResponse.class);
    }

    @Override
    public Mono<Analysis> projectAnalysis () {
        return null;
    }


//    public void consume() {
//
//
//        Mono<String> helloMono = client.get()
//                .uri("/")
//                .retrieve()
//                .bodyToMono(String.class);
//
//        helloMono.subscribe(hello -> LOGGER.info("This should be hello world: {}", hello));
//
//
//        LinkedMultiValueMap<String, String> tokenData = new LinkedMultiValueMap<>();
//        tokenData.add("name", randomString());
//
//        Mono<Object> createToken = client.post()
//                .uri("/token").bodyValue(tokenData)
//                .retrieve()
//                .bodyToMono(Object.class);
//
//        createToken.subscribe(project -> LOGGER.info("New usertoken: {}", project));
//
//
//        LinkedMultiValueMap<String, String> projectData = new LinkedMultiValueMap<>();
//        projectData.add("name", uuid);
//        projectData.add("project", uuid);
//
//        Mono<Object> createProject = client.post()
//                .uri("/create").bodyValue(projectData)
//                .retrieve()
//                .bodyToMono(Object.class);
//
//        createProject.subscribe(project -> LOGGER.info("Created project: {}", project));
//
//
//        Flux<Object> getAllProjects = client.get()
//                .uri("/search")
//                .retrieve()
//                .bodyToFlux(Object.class);
//
//        getAllProjects.subscribe(project -> LOGGER.info("All existing projects: {}", project));
//
//
//        Flux<Object> projectAnalysis = client.get()
//                .uri(builder -> builder.path("/analysis")
//                        .queryParam("project", "First")
//                        .build())
//                .retrieve()
//                .bodyToFlux(Object.class);
//
//        projectAnalysis.subscribe(project -> LOGGER.info("Project analysis result: {}", project));
//    }
}