package com.kqgeo.seastar.transaction;

import com.kqgeo.seastar.transaction.util.JsonUtils;
import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;

public class TraceDefinitionTests {


    @Test
    public void testJsonSerialize() throws Exception {


        for (int i = 0; i < 1000; i++) {


            SpanDefinition span = new SpanDefinition();

            span.setId(100 + i);
            span.setKind(SpanDefinition.Kind.COORDINATOR);
            span.setLocalEndpoint("http://localhost:81/endpoint");
            span.setTraceId("traceId");
            span.setPropagation(Propagation.NESTED);

            long st = System.currentTimeMillis();
            String json = JsonUtils.stringify(span);

            System.out.println(json);

            SpanDefinition spanD = JsonUtils.parse(json, SpanDefinition.class);

            long et = System.currentTimeMillis();

            System.out.println(et - st);

//            System.out.println(json);
        }


    }

//    @Test
//    public void testKryoSerialize() throws Exception {
//        Kryo kryo = new Kryo();
//
//        for (int i = 0; i < 1000; i++) {
//
//
//            SpanDefinition span = new SpanDefinition();
//
//            span.setId(100 + i);
//            span.setKind(SpanDefinition.Kind.PARTICIPATOR);
//            span.setLocalEndpoint("http://localhost:81/endpoint");
//            span.setTraceId("traceId");
//
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//
//            Output output = new Output(bytes);
//            long st = System.currentTimeMillis();
//
//            kryo.writeObject(output, span);
//            output.close();
//
//            byte[] spanBytes = bytes.toByteArray();
//
//            String kryoString = Base64Utils.encodeToString(spanBytes);
//
//
//            Input input = new Input(Base64Utils.decodeFromString(kryoString));
//
//            SpanDefinition spanS = kryo.readObject(input, SpanDefinition.class);
//            input.close();
//
//            long et = System.currentTimeMillis();
//
//            System.out.println(et - st);
//
//
////            System.out.println(kryoString);
////            System.out.println(spanS);
//        }
//
//
//    }
}
