package org.five.sonarqubot.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
public class HttpRequests {
    @GetMapping("/")
    public String helloWorld() {
        return "Hello World! ";
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getProjects() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9000/api/projects/search";
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, authHeader(), Object.class);
        response.getBody();
        return response;

    }

    @PostMapping(path = "/project")
    public ResponseEntity<Object> create(@RequestParam String name, @RequestParam String project, @RequestParam String visibility) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9000/api/projects/create" +
                "?name=" + name + "&project=" + project + "&visibility=" + visibility;

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, authHeader(), Object.class, HttpStatus.CREATED);
        response.getBody();
        return response;

    }

    @GetMapping("/analyses")
    public ResponseEntity<Object> getAnalysesByProject(@RequestParam String project) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9000/api/measures/search_history" + "?component=" + project + "&metrics=" + metrics();
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, authHeader(), Object.class);
        response.getBody();
        return response;

    }

    /*To delete a project you need the project's key, this is the value in "project" when creating a project */
    @PostMapping(path = "/delete")
    public ResponseEntity<Object> deleteByProject(@RequestParam String project) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9000/api/projects/delete" +
                "?project=" + project;

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, authHeader(), Object.class, HttpStatus.CREATED);
        response.getBody();
        return response;

    }
    //TODO= deleteAll needs "At least one parameter among analyzedBefore, projects and q must be provided"
    @PostMapping(path = "/deleteAll")
    public ResponseEntity<Object> deleteAll() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9000/api/projects/bulk_delete";

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, authHeader(), Object.class);
        response.getBody();
        return response;

    }


    public HttpEntity<String> authHeader() {
        String plainCreds = "admin:admin";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        return new HttpEntity<>(headers);
    }

    public String metrics() {
        String[] metricsArray = {"coverage", "bugs", "new_violations", "lines", "statements"};
        String metrics = String.join(",", metricsArray);
        return metrics;
    }

}









