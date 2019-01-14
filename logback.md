# logback机制
* 只需要引入logback-classic即可,他里面引入了slf4j-api.jar
## bind()SPI加载机制
* StaticLoggerBinder.getSingleton()获取单例StaticLoggerBinder
* 获取LoggerContext日志上下文对象
    * 它实现了ILoggerFactory对象
    * 初始化ch.qos.logback.classic.Logger对象,它实现了org.slf4j.Logger
    * 初始化日志level等
* new ContextInitializer(defaultLoggerContext).autoConfig()自动扫描配置文件,默认会扫描logback.xml文件并初始化
* 初始化上下文选择器
    * 如果配置了JNDI会初试化ContextJNDISelector
    * 默认初始化DefaultContextSelector,没什么作用
    * 动态加载定制化选择器,可以自己进行扩展,比如根据定制策略进行选择等

## Marker标记对象机制
* 它可以给某条消息加一个标记
* 由slf4j提供了默认的实现工厂BasicMarkerFactory创建Marker对象
* 使用方式
```java
String firstMarker = "FIRST_MARKER";
Marker marker = MarkerFactory.getMarker(firstMarker);

logger.info(marker,"second slf4j demo");
```
```xml
<turboFilter class="ch.qos.logback.classic.turbo.MarkerFilter">
    <Name>CONFIDENTIAL_FILTER</Name>
    <Marker>FIRST_MARKER</Marker>
    <OnMatch>DENY</OnMatch>
</turboFilter>
```
* second slf4j demo这条消息将不会打印,<Marker>FIRST_MARKER</Marker>的值需与String firstMarker = "FIRST_MARKER"此值一样,否则匹配不上

## MDC机制
* 调用MDCAdapter.put(key, val)
* MDC初始化时会通过SPI机制加载StaticMDCBinder,获取MDCAdapter的实现类LogbackMDCAdapter
* 其维护了一个本地线程变量ThreadLocal<Map<String, String>> copyOnThreadLocal
* MDC缓存的值是与线程绑定的
```java
MDC.put("lori_id","i am lori");
```
* 可以在日志上下文中缓存key为lori_id的数据
```xml
%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} %X{lori_id} - %msg%n
```
* 在配置文件通过%X配置,就可以在输入的日志中输出
```java
11:04:10.281 [main] INFO  com.example.log.LogSlf4jDemo i am lori - first slf4j demo
```
* MDC可以用于线上问题日志定位
## <springProfile>
* 可以根据环境控制日志输出
* 不能使用默认的日志文件名:logback.xml
* 可以使用启动参数-Dspring.profiles.active=showInConsole设置profile
* 也可以通过硬编码的方式
```java
public class StandaloneProfileApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> ,PriorityOrdered{

    public static final String STANDALONE_MODE_PROPERTY_NAME = "showInConsole.flag";

    String STANDALONE_SPRING_PROFILE = "showInConsole";

    /**
     * 一定要设置order在最先执行,否则已经加载了logback.xml,springProfile就无效了
     * @param event
     */

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        if (!environment.getProperty(STANDALONE_MODE_PROPERTY_NAME,boolean.class,false)){
            environment.addActiveProfile(STANDALONE_SPRING_PROFILE);
        }
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
```
* 注意一定要设置顺序为最高级,否则设置的环境变量为无效
## <springProperty>
* 原理与<springProfile>一样,可以从spring Environment获取配置项,进行日志输出的控制