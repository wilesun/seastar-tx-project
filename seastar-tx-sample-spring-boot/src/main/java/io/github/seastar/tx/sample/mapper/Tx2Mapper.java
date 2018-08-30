package io.github.seastar.tx.sample.mapper;

import io.github.seastar.tx.sample.model.Tx1Model;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Tx2Mapper {


    List<Tx1Model> select1();

    void insert1();
}
