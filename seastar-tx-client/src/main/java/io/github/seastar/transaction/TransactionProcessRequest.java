package io.github.seastar.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionProcessRequest {

    public static final int NOT_STACK_ID = -1;


    String traceId;

    Integer stackId;

    Integer spanId;

//    boolean localProcess;

    String endpoint;


}
