package io.github.seastar.tx.sample;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;

public class JsonParseTests {

    @Test
    public void testParse1() throws Exception {

        ClassPathResource resource = new ClassPathResource("test_1.json");

        InputStream inputStream = resource.getInputStream();

        String jsonString = IOUtils.toString(inputStream, "utf-8");

        inputStream.close();


        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setRequestFactory();


        for (int i = 0; i < 1000; i++) {
            long s = System.currentTimeMillis();


            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> traceHttpEntity = new HttpEntity<>(jsonString, headers);
            String s1 = restTemplate.postForObject("http://localhost:8851/trace", traceHttpEntity, String.class);


            long e = System.currentTimeMillis();

            System.out.println((e - s) + "ms");

        }

    }
}
