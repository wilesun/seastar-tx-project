package com.kqgeo.seastar.tx.sample;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JsonTimeTests {


    @Autowired
    private RestTemplate restTemplate;


    @Test
    public void testParse1() throws Exception {

        restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory());

        ClassPathResource resource = new ClassPathResource("test_1.json");

        InputStream inputStream = resource.getInputStream();

        String jsonString = IOUtils.toString(inputStream, "utf-8");

        inputStream.close();


        for (int i = 0; i < 1000; i++) {
            long s = System.currentTimeMillis();


            for (int j = 0; j < 20; j++) {

                String ss2 = restTemplate.getForObject("http://localhost:8851/trace/576c69b0-5e7d-4452-85a0-d2edf0985842", String.class);

                HttpHeaders headers = new HttpHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                headers.add("Accept", MediaType.APPLICATION_JSON.toString());
                HttpEntity<String> traceHttpEntity = new HttpEntity<>(jsonString, headers);
                String s1 = restTemplate.postForObject("http://localhost:8851/trace", traceHttpEntity, String.class);
            }


            long e = System.currentTimeMillis();

            System.out.println((e - s) + "ms");

        }

    }
}
