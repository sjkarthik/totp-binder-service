package io.mosip.totpbinderservice.DTO;

import io.mosip.totpbinderservice.helper.Constants;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KeyBindRequestDTO {
    private String key;
    private String accessToken;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public MultiValueMap<String, String> toMap() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(Constants.KEY, this.key);
        return map;
    }
}