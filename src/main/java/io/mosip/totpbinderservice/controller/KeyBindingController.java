package io.mosip.totpbinderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.mosip.totpbinderservice.dto.AccessTokenResponse;
import io.mosip.totpbinderservice.dto.KeyBindRequestDTO;
import io.mosip.totpbinderservice.dto.KeyBindResponseDTO;
import io.mosip.totpbinderservice.dto.RequestWrapper;
import io.mosip.totpbinderservice.dto.ResponseWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
    public KeyBindResponseDTO bindKey (@RequestBody KeyBindRequestDTO bindRequestDTO, @RequestHeader("Authorization") String bearerToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", bearerToken);
        
        RequestWrapper<KeyBindRequestDTO> request = new RequestWrapper<KeyBindRequestDTO>();
        request.setRequest(bindRequestDTO);
        request.setRequestTime(LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(bindingEndpoint);
        HttpEntity<RequestWrapper<KeyBindRequestDTO>> entity = new HttpEntity<>(request, headers);
        //ResponseEntity<String> responseEntity = null;

        try {
            var responseEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST,
                    entity, ResponseWrapper.class);
            
            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                var responseWrapper = responseEntity.getBody();
                KeyBindResponseDTO bindingResponse = objectMapper.convertValue(responseWrapper.getResponse(), KeyBindResponseDTO.class);
                return bindingResponse;
            }

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // handle exceptions
        }
        
        return new KeyBindResponseDTO();
    }

}
