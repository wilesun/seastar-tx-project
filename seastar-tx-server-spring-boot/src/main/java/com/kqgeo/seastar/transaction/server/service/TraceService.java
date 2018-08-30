package com.kqgeo.seastar.transaction.server.service;

import com.kqgeo.seastar.transaction.TraceDefinition;

import java.util.List;


public interface TraceService {


    TraceDefinition findOneById(String traceId);

    List<TraceDefinition> findAll();

    TraceDefinition update(TraceDefinition newTrace);

    TraceDefinition delete(String traceId);

    int clear();
}
