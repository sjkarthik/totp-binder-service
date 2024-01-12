package io.mosip.totpbinderservice.DTO;

import io.mosip.totpbinderservice.helper.Constants;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KeyBindRequestDTO {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MultiValueMap<String, String> toMap() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(Constants.KEY, this.key);
        return map;
    }
}