package org.five.sonarqubot.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
public class HttpRequests {
    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World! ";
    }

    @GetMapping("/restController")
    public ResponseEntity<Object> restController() {


        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9000/api/webservices/list";
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, authHeader(), Object.class);
        response.getBody();
        return response;

    }

    @PostMapping(path = "/project")
    public ResponseEntity<Object> create(@RequestParam String name, @RequestParam String project, @RequestParam String visibility) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9000/api/projects/create" +
                "?name=" + name + "&project=" + project + "&visibility=" + visibility;

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, authHeader(), Object.class,HttpStatus.CREATED);
        response.getBody();
        return response;

    }


    public HttpEntity<String> authHeader() {
        String plainCreds = "admin:isa";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        return new HttpEntity<>(headers);
    }

}









