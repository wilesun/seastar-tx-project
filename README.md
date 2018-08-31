# seastar-tx-project
Distributed Transaction Projects
### 介绍
重写 spring 原有的 AbstractPlatformTransactionManager 类是使用原生的 @Transactional 注解来支持分布式事务 . 
分布式事务在底层处理的时候, 使用 Restful 接口来提交分布式事务. 