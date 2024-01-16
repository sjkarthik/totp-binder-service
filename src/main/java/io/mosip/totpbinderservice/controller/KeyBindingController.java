package io.mosip.totpbinderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mosip.totpbinderservice.DTO.AccessTokenResponse;
import io.mosip.totpbinderservice.DTO.KeyBindRequestDTO;
import io.mosip.totpbinderservice.DTO.KeyBindResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/binding")
public class KeyBindingController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${mosip.iam.binding_endpoint}")
    private String bindingEndpoint;

    @PostMapping("/totp-key-bind")
    public KeyBindResponseDTO bindKey (@RequestBody KeyBindRequestDTO bindRequestDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + bindRequestDTO.getAccessToken());

        MultiValueMap<String, String> totpKey = bindRequestDTO.toMap();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(bindingEndpoint);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(totpKey, headers);
        ResponseEntity<String> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST,
                    entity, String.class);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // handle exceptions
        }

        KeyBindResponseDTO keyBindResponseDTO = null;
        try {
            keyBindResponseDTO = objectMapper.readValue(responseEntity.getBody(), KeyBindResponseDTO.class);
        } catch (IOException exception) {
            // handle exceptions
        }

        System.out.println("Token bind confirmed");
        return keyBindResponseDTO;
    }

}
