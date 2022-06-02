package org.five.sonarqubot.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@PropertySource("classpath:app.properties")

public class HttpRequests {
    @Value("${user}")
    private String user;
    @Value("${password}")
    private String password;
    @Value("${sonarqubeAPI}")
    private String sonarAPI;

    @GetMapping("/")
    public String helloWorld() {
        return "Hello World! ";
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getProjects() {

        RestTemplate restTemplate = new RestTemplate();
        String url = sonarAPI + "/projects/search";
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, authHeader(), Object.class);
        response.getBody();
        return response;

    }

    @PostMapping(path = "/project")
    public ResponseEntity<Object> create(@RequestParam String name, @RequestParam String project, @RequestParam String visibility) {

        RestTemplate restTemplate = new RestTemplate();
        String url = sonarAPI + "projects/create" +
                "?name=" + name + "&project=" + project + "&visibility=" + visibility;

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, authHeader(), Object.class, HttpStatus.CREATED);
        response.getBody();
        return response;

    }

    @GetMapping("/analyses")
    public ResponseEntity<Object> getAnalysesByProject(@RequestParam String project) {

        RestTemplate restTemplate = new RestTemplate();
        String url = sonarAPI + "/measures/search_history" + "?component=" + project + "&metrics=" + metrics();
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, authHeader(), Object.class);
        response.getBody();
        return response;

    }

    /*To delete a project you need the project's key, this is the value in "project" when creating a project */
    @PostMapping(path = "/delete")
    public ResponseEntity<Object> deleteByProject(@RequestParam String project) {

        RestTemplate restTemplate = new RestTemplate();
        String url = sonarAPI + "/projects/delete" +
                "?project=" + project;

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, authHeader(), Object.class, HttpStatus.NO_CONTENT);
        response.getBody();
        return response;

    }

    //TODO= deleteAll needs "At least one parameter among analyzedBefore, projects and q must be provided"
    @PostMapping(path = "/deleteAll")
    public ResponseEntity<Object> deleteAll() {

        RestTemplate restTemplate = new RestTemplate();
        String url = sonarAPI + "/projects/bulk_delete";

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, authHeader(), Object.class);
        response.getBody();
        return response;

    }


    private HttpEntity<String> authHeader() {
        String plainCreds = user + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        return new HttpEntity<>(headers);
    }

    private String metrics() {
        String[] metricsArray = {"coverage", "bugs", "new_violations", "lines", "statements"};
        String metrics = String.join(",", metricsArray);
        return metrics;
    }

}









