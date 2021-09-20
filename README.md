## log4j2-demo

### 项目简介：
项目Web框架使用SpringBoot，日志使用log4j2。用来压测复现同步打印异常日志时服务性能骤降的问题，以及对这个问题进行代码调试分析

#### 压测

##### 目的
比较**请求正常**和**请求异常同步打印异常日志**两种场景下服务的性能

##### 压测命令
正常：`wrk -t8 -c50 -d60 --latency --timeout 10 http://127.0.0.1:8080/ok`
异常：`wrk -t8 -c50 -d60 --latency --timeout 10 http://127.0.0.1:8080/bad`

##### 压测结果
压测条件 | 平均响应时间 | QPS | QPS比较 | 结论
------------ | ------------ | ------------ | ------------ | ------------
正常|947.91| 49.39 | - | - |
异常|5423.33|7.54|-85%|打印异常栈，QPS急剧下降85%

#### 代码调试

##### 目的
分析打印异常日志时每次都会加载类`sun.reflect.GeneratedMethodAccessor`，这个操作会比较耗时，在并发大的情况下会产生线程间的同步等待，导致线程阻塞，影响服务性能。

##### 调试过程
1、使用VSCode打开项目并调试
打开`Log4j2DemoApplication.java`，点击debug，开启项目调试

2、设置2个断点
* `com.kuaikan.log4j2demo` 17行
* `org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedWebappClassLoader.loadClass` 70行

3、执行脚本
`for i in `seq 16`; do curl http://127.0.0.1:8080/bad; done;`
> 前15次类加载使用

`curl http://127.0.0.1:8080/bad;`

4、在断点处查看相关`name`变量信息

#### 结论
建议使用异步打印日志

