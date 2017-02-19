import com.apress.pss.common.util.HeaderUtil;
import com.apress.pss.security.transfer.JwtUserDto;
import com.apress.pss.security.util.JwtTokenValidator;
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
        HttpHeaders headers = new HttpHeaders();
        JwtUserDto user = new JwtUserDto();
        user.setId(11L);
        user.setRole("admin");
        user.setUsername("car");
        String token = JwtTokenValidator.generateToken(user);
        headers.add("Authorization", "Basic " + token);

        RestTemplate template = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(headers);
//        ResponseEntity<Object> result = template.exchange(url+"hello", HttpMethod.GET, request, Object.class);
//        System.out.println(result);

//        String result2 = template.getForObject(url + "admin/movies", String.class);
//        System.out.println(result2);

        String result3 = template.postForObject(url + "admin/movies", request, String.class);
        System.out.println(result3);
    }


}
