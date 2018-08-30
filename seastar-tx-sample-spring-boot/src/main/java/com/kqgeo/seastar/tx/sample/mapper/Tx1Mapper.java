package com.kqgeo.seastar.tx.sample.mapper;

import com.kqgeo.seastar.tx.sample.model.Tx1Model;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Tx1Mapper {


    List<Tx1Model> select1();

    void insert1();
}
