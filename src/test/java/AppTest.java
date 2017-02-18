import org.junit.Test;
import org.springframework.http.*;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by CHANEL on 2017/2/18.
 */
public class AppTest {

    private String url = "http://localhost:8888/";

    @Test
    public void testBasicAuthentication() {
        RestTemplate template = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders("car:scarvarez"));
//        ResponseEntity<Object> result = template.exchange(url+"hello", HttpMethod.GET, request, Object.class);
//        System.out.println(result);

//        String result2 = template.getForObject(url + "admin/movies", String.class);
//        System.out.println(result2);

        String result3 = template.postForObject(url + "admin/movies", request, String.class);
        System.out.println(result3);
    }

    /*
     * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
     */
    private static HttpHeaders getHeaders(String usernameAndPassword) {

        String base64Credentials = encodeByBase64(usernameAndPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static String encodeByBase64(String str){
        String plainCredentials = str;
        String base64Credentials = new String(Base64.encode(plainCredentials.getBytes()));
        return base64Credentials;
    }
}
