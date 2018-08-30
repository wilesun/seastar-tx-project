package io.github.seastar.tx.sample;

import io.github.seastar.transaction.TraceContext;
import io.github.seastar.transaction.TraceSyncService;
import io.github.seastar.tx.sample.service.Tx3Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Tx3Tests {

    @Autowired
    private Tx3Service tx3Service;


    @Autowired
    private TraceSyncService traceSyncService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplate restTemplate;


    @Test
    public void testSelect() throws Exception {

        tx3Service.selectList();


        TimeUnit.MINUTES.sleep(10);

    }

    @Test
    public void testInsert1() throws Exception {

        tx3Service.insert1("");


        TraceContext traceContext = TraceContext.getContext();

        System.out.println();

    }

    @Test
    public void testInsert2() throws Exception {

        for (int i = 0; i < 200; i++) {

            long s = System.currentTimeMillis();

            String str = restTemplate.getForObject("http://192.168.0.189:9831/tx-app1/test/trace/hi", String.class);


            long e = System.currentTimeMillis();

            System.out.println((e - s) + "ms");
        }
//        tx3Service.insert2();
    }

    @Test
    public void testInsert3() throws Exception {


        ExecutorService executorService = Executors.newFixedThreadPool(10);

        AtomicInteger atomicInteger = new AtomicInteger();

        for (int i = 0; i < 4; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 20; j++) {

                        long s = System.currentTimeMillis();

                        try {
                            tx3Service.insert2();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        long e = System.currentTimeMillis();

                        System.out.println((e - s) + "ms");
                    }
                }
            });

        }


//        tx3Service.insert2();

        System.out.println("执行完成");

        TimeUnit.MINUTES.sleep(10);
    }

    @Test
    public void testInsertRollback1() throws Exception {
        long s = System.currentTimeMillis();

        tx3Service.insert2();

        long e = System.currentTimeMillis();
    }


//    @Test
//    public void testSync1() throws Exception {
//        ClassPathResource resource = new ClassPathResource("test_1.json");
//
//        InputStream inputStream = resource.getInputStream();
//
//        String jsonString = IOUtils.toString(inputStream, "utf-8");
//
//        inputStream.close();
//
//        for (int i = 0; i < 1000; i++) {
//            long s = System.currentTimeMillis();
//            ObjectMapper objectMapper = new ObjectMapper();
//            TraceDefinition trace = objectMapper.readValue(jsonString, TraceDefinition.class);
////            System.out.println(trace);
//
//            traceSyncService.syncTrace(trace);
//
//            long e = System.currentTimeMillis();
//
//            System.out.println((e - s) + "ms");
//
//        }
//
//    }
//
//    @Test
//    public void testOkHttp3Sync() throws Exception {
//        ClassPathResource resource = new ClassPathResource("test_1.json");
//
//        InputStream inputStream = resource.getInputStream();
//
//        String jsonString = IOUtils.toString(inputStream, "utf-8");
//
//        inputStream.close();
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        for (int i = 0; i < 1000; i++) {
//            long s = System.currentTimeMillis();
//
//            String url = "http://localhost:8851/trace";
//            final Request request = new Request.Builder()
//                    .url(url)
//                    .post(RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), jsonString))
//                    .build();
//            Call call = okHttpClient.newCall(request);
//
//            Response response = call.execute();
//
//            String resJsonString = response.body().string();
//            response.close();
////            System.out.println(resJsonString);
//
//
//            long e = System.currentTimeMillis();
//
//            System.out.println((e - s) + "ms");
//
//        }
//    }
//
//    @Test
//    public void testHttpClientSync() throws Exception {
//        ClassPathResource resource = new ClassPathResource("test_1.json");
//
//        InputStream inputStream = resource.getInputStream();
//
//        String jsonString = IOUtils.toString(inputStream, "utf-8");
//
//        inputStream.close();
//
//        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
//        connectionManager.setMaxTotal(200);
//        connectionManager.setDefaultMaxPerRoute(20);
//        HttpClient httpClient = HttpClients
//                .custom()
//                .setConnectionManager(connectionManager)
////                .setDefaultRequestConfig(RequestConfig.custom().setStaleConnectionCheckEnabled(true).build())
//                .build();
//
//
//        for (int i = 0; i < 1000; i++) {
//
//            long s = System.currentTimeMillis();
//
//
//            String url = "http://localhost:8851/trace";
//            HttpPost post = new HttpPost(url);
//
//            post.setHeader("Content-type", "application/json; charset=utf-8");
//            StringEntity entity = new StringEntity(jsonString, Charset.forName("UTF-8"));
//            entity.setContentEncoding("UTF-8");
//            entity.setContentType("application/json");
//            post.setEntity(entity);
//
//            HttpResponse httpResponse = httpClient.execute(post);
//
//            HttpEntity responseEntity = httpResponse.getEntity();
//
//            String responseJsonString =   EntityUtils.toString(responseEntity);
//
//            TraceDefinition trace = JsonUtils.parse(responseJsonString, TraceDefinition.class);
////            System.out.println(trace);
//            post.releaseConnection();
//
//            long e = System.currentTimeMillis();
//
//            System.out.println((e - s) + "ms");
//
//        }
//    }
//
//    private UriTemplateHandler uriTemplateHandler = new DefaultUriBuilderFactory();
//
//    @Test
//    public void testTransactionSuccess() throws Exception {
//
//
//        String rootUri = testRestTemplate.getRootUri();
//        System.out.println(rootUri);
//
////        String hello = testRestTemplate.getForObject("/transaction/success", String.class);
////        System.out.println(hello);
//    }
}
