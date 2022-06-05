package org.five.sonarqubot.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
public class SonarController {
    @Value("${user}")
    private String user;
    @Value("${password}")
    private String password;
    @Value("${sonar.api.url}")
    private String sonarAPI;

    @GetMapping("/")
    public String helloWorld() {
        return "Hello World! ";
    }


    @PostMapping(path = "/token")
    public ResponseEntity<String> createToken(@RequestBody MultiValueMap<String,String> tokenData) {
        RestTemplate restTemplate = new RestTemplate();
        String url = sonarAPI + "/user_tokens/generate";

        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(tokenData, contentHeader());

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class, HttpMethod.POST);
        response.getBody();
        return response;
    }
    @GetMapping("/search")
    public ResponseEntity<Object> getProjects() {

        RestTemplate restTemplate = new RestTemplate();
        String url = sonarAPI + "/projects/search";
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, authHeader(), Object.class);
        response.getBody();
        return response;

    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> createProject(@RequestBody MultiValueMap<String,String> formData) {
        RestTemplate restTemplate = new RestTemplate();
        String url = sonarAPI + "/projects/create";

        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(formData, contentHeader());

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class, HttpMethod.POST);
        response.getBody();
        return response;
    }

    @GetMapping("/analysis")
    public ResponseEntity<Object> getAnalysisByProject(@RequestParam String project) {

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
        String url = sonarAPI + "/projects/delete" + "?project=" + project;

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
    public String base64Creds() {
        String plainCreds = user + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        return new String(base64CredsBytes);
    }


    private HttpEntity<String> authHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds());
        return new HttpEntity<>(headers);
    }

    private HttpHeaders contentHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + base64Creds());
        return new HttpHeaders(headers);
    }

    private String metrics() {
        String[] metricsArray = {"code_smells", "bugs", "duplicated_lines"};
        return String.join(",", metricsArray);
    }

}









