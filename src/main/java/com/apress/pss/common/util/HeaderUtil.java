package com.apress.pss.common.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.codec.Base64;

import java.util.Arrays;

/**
 * Created by CHANEL on 2017/2/19.
 */
public class HeaderUtil {
    /*
     * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
     */
    public static HttpHeaders getHeaders(String usernameAndPassword) {

        String base64Credentials = encodeByBase64(usernameAndPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public static String encodeByBase64(String str){
        String plainCredentials = str;
        String base64Credentials = new String(Base64.encode(plainCredentials.getBytes()));
        return base64Credentials;
    }
}
