package io.github.seastar.tx.app1.mapper;

import io.github.seastar.tx.app1.model.Tx1Model;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface App1Tx1Mapper {


    List<Tx1Model> select1();

    void insert1();
}
