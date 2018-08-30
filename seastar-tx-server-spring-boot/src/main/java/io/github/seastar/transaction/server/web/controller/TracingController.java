package io.github.seastar.transaction.server.web.controller;

import io.github.seastar.transaction.TraceDefinition;
import io.github.seastar.transaction.server.service.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TracingController {

    @Autowired
    private TraceService traceService;

    @GetMapping("/trace/{traceId}")
    public TraceDefinition tracingGet(@PathVariable("traceId") String traceId) {
        return traceService.findOneById(traceId);
    }

    @GetMapping("/traces")
    public List<TraceDefinition> tracingsGet() {
        return traceService.findAll();
    }


    @PostMapping("/trace")
    public TraceDefinition tracingPost(@RequestBody TraceDefinition trace) {
        long s = System.currentTimeMillis();
        TraceDefinition newTrace = traceService.update(trace);
        long e = System.currentTimeMillis();
        System.out.println("server trace parse using: " + (e - s) + "ms");
        return newTrace;
    }


//    @PostMapping("/trace")
//    public TraceDefinition tracingPost(@RequestBody String trace) throws Exception {
//        long s = System.currentTimeMillis();
//        TraceDefinition newTrace = traceService.update(JsonUtils.parse(trace, TraceDefinition.class));
//        long e = System.currentTimeMillis();
//        System.out.println("server trace parse using: " + (e - s) + "ms");
//        return newTrace;
//    }

//    @PostMapping("/trace")
//    public String tracingPost(@RequestBody String trace) throws Exception {
////        TraceDefinition traceDef = JsonUtils.parse(trace, TraceDefinition.class);
//
////        long s = System.currentTimeMillis();
//////        TraceDefinition traceDef = KryoUtils.deserialize(trace, TraceDefinition.class);
////
////        long e = System.currentTimeMillis();
////
////        System.out.println("server trace parse using: " + (e - s) + "ms");
////        TraceDefinition newTrace = traceService.update(trace);
////        return newTrace;
////        return traceDef;
//        return trace;
//    }


    @DeleteMapping("/trace/{traceId}")
    public TraceDefinition tracingDelete(@PathVariable("traceId") String traceId) {
        return traceService.delete(traceId);
    }


    @DeleteMapping("/traces")
    public int tracingsDelete() {
        return traceService.clear();
    }

}
