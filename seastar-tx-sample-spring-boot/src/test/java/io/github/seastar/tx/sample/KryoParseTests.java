package io.github.seastar.tx.sample;

public class KryoParseTests {

//    @Test
//    public void testParse1() throws Exception {
//
//        ClassPathResource resource = new ClassPathResource("test_1.json");
//
//        InputStream inputStream = resource.getInputStream();
//
//        String jsonString = IOUtils.toString(inputStream, "utf-8");
//
//        inputStream.close();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        TraceDefinition trace = objectMapper.readValue(jsonString, TraceDefinition.class);
//
//        for (int i = 0; i < 1000; i++) {
//            long s = System.currentTimeMillis();
////            System.out.println(trace);
//
//            String ser = KryoUtils.serialize(trace);
//
//            TraceDefinition traceDef = KryoUtils.deserialize(ser, TraceDefinition.class);
//            long e = System.currentTimeMillis();
//
//            System.out.println((e - s) + "ms");
//
//        }
//
//    }
}
