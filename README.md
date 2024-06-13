[//]: # (<h1>)

[//]: # (<p align="center">)

[//]: # (	<strong>愿世界和平</strong>)

[//]: # (</p>)

[//]: # (</h1>)

[//]: # ()
[//]: # (<p align="center">)

[//]: # (    铭记苦难，心系巴勒斯坦，哀悼逝者。)

[//]: # (    <br>中国屹立东方，自强自立，方能更好地守护和平，担当国际责任，为世界公正贡献力量。)

[//]: # (    <br>愿悲剧不再重演，和平永驻人间。)

[//]: # (    <br />)

[//]: # (    <img src="./img/img.png" alt="Easy-Manager-Tool" width="75" style="align-content: center" /> )

[//]: # (    <span>Peace And Love</span>)

[//]: # (    <img src="./img/img_1.png" alt="Easy-Manager-Tool" width="99" style="align-content: center" />)

[//]: # (</p>)

[//]: # ()
[//]: # (<br />)

[//]: # (<br />)

[//]: # (<br />)

[//]: # (<br />)

[//]: # (<br />)

[//]: # (<br />)

[//]: # ()

<h1>
<p align="center">
	<strong>腾讯元器SDK-JAVA</strong>
</p>
</h1>
<p align="center">
    腾讯元器智能体 API Java SDK 是对腾讯元器智能体的API进行了封装，方便Java开发者接入系统调用。
</p>



# 接入方式 

## 引入依赖
### Maven
```yaml

<dependency>
    <groupId>com.yby6.yuanqi</groupId>
    <artifactId>yuanqi-sdk-java</artifactId>
    <version>1.2</version>
</dependency>

```
### Gradle
```yaml

implementation group: 'com.yby6.yuanqi', name: 'yuanqi-sdk-java', version: '1.2'

```

## 单元测试

```java

@Slf4j
public class YuanQIAPI {

    private YuanQiSession yuanQiSession;

    /**
     * 初始化会话工厂
     */
    @Before
    public void test_OpenAiSessionFactory() {
        // 1. 配置文件
        YuanQiConfiguration yuanQiConfiguration = new YuanQiConfiguration();
        yuanQiConfiguration.setApiHost("https://yuanqi.tencent.com/openapi/");
        yuanQiConfiguration.setApiKey("你的智能体APIKEY");
        yuanQiConfiguration.setLevel(HttpLoggingInterceptor.Level.NONE);
        // 2. 会话工厂
        DefaultYuanQiSessionFactory factory = new DefaultYuanQiSessionFactory(yuanQiConfiguration);
        // 3. 开启会话
        this.yuanQiSession = factory.openSession();
        log.info("openAiSession:{}", yuanQiSession);
    }

    /**
     * 返回参数:
     * {
     * "id": "43b88ac7daab69d28e6320ae9ea39dc5",
     * "created": 1717177745,
     * "choices": [
     * {
     * "finish_reason": "stop",
     * "message": {
     * "role": "assistant",
     * "content": "我是隔壁老王，一个无所不能的智能体，分分钟秒掉问题！",
     * "steps": [
     * {
     * "role": "assistant",
     * "content": "我是隔壁老王，一个无所不能的智能体，分分钟秒掉问题！",
     * "usage": {
     * "prompt_tokens": 252,
     * "completion_tokens": 15,
     * "total_tokens": 267
     * },
     * "time_cost": 1718
     * }
     * ]
     * }
     * }
     * ],
     * "assistant_id": "mmbnqMnLdYz0",
     * "usage": {
     * "prompt_tokens": 252,
     * "completion_tokens": 15,
     * "total_tokens": 267
     * }
     * }
     */
    @Test
    public void test_chat_completions() {
        // 1. 创建参数
        YuanQiCompletionRequest chatCompletion = YuanQiCompletionRequest
                .builder()
                .messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content(
                        Collections.singletonList(
                                MessageContent.builder().type(Constants.Type.TEXT)
                                        .text("你是谁啊?").build()
                        )
                ).build()))
                .userId("rodneyxiong")
                .assistantId("mmbnqMnLdYz0")
                .stream(false)
                .build();
        // 2. 发起请求
        YuanQiCompletionResponse yuanQiCompletionResponse = yuanQiSession.completions(chatCompletion);
        // 3. 解析结果
        yuanQiCompletionResponse.getChoices().forEach(e -> {
            log.info("测试结果：{}", e.getMessage());
            log.info("消息: {}", e.getMessage().getContent());
        });
    }

    /**
     * 流试返回参数:
     * {
     * "id": "82956d810a0ff9b413c8bf924c2190c3",
     * "created": 1717177832,
     * "choices": [
     * {
     * "delta": {
     * "role": "assistant",
     * "content": "我是",
     * "time_cost": 0
     * }
     * }
     * ],
     * "assistant_id": "mmbnqMnLdYz0",
     * "usage": {
     * "prompt_tokens": 0,
     * "completion_tokens": 0,
     * "total_tokens": 0
     * }
     * }
     */
    @Test
    public void test_chat_completions_stream() throws JsonProcessingException, InterruptedException {
        // 1. 创建参数
        YuanQiCompletionRequest chatCompletion = YuanQiCompletionRequest
                .builder()
                .messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content(
                        Collections.singletonList(
                                MessageContent.builder().type(Constants.Type.TEXT)
                                        .text("老王,你可以给我解析这个URL吗?  https://yby.com").build()
                        )
                ).build()))
                .userId("rodneyxiong")
                .assistantId("mmbnqMnLdYz0")
                .stream(true)
                .build();
        // 2. 发起请求
        EventSource eventSource = yuanQiSession.chatCompletions(chatCompletion, new EventSourceListener() {
            @Override
            public void onEvent(@NotNull EventSource eventSource, String id, String type, @NotNull String data) {
                try {
                    if (data.equals("[DONE]")) {
                        return;
                    }

                    YuanQiCompletionResponse response = JSONUtil.toBean(data, YuanQiCompletionResponse.class);

                    List<ChatChoice> choices = response.getChoices();
                    for (ChatChoice chatChoice : choices) {
                        final ChatChoice.MessageDTO.StepsDTO delta = chatChoice.getDelta();

                        // 应答完成
                        String finishReason = chatChoice.getFinishReason();
                        if (StrUtil.isNotBlank(finishReason) && "stop".equals(finishReason)) {
                            break;
                        }

                        // 发送信息
                        try {
                            if (null != delta) {
                                log.info(delta.getContent());
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(EventSource eventSource, Throwable t, Response response) {
                log.error("错误: {}, {}", response.code(), t.getMessage());
            }
        });
        // 等待
        new CountDownLatch(1).await();
    }

}


```


# SpringBoot 引用

```java


@Data
@ConfigurationProperties(prefix = "yuanqi.sdk.config", ignoreInvalidFields = true)
public class YuanQiSDKConfigProperties {

  /**
   * 转发地址
   */
  private String apiHost;
  /**
  * 智能体API KEY 必须填写
  */
  private String apiKey;

  private String assistantId;
  private String userId;

}


```

```java
@Slf4j
@Configuration
@EnableConfigurationProperties(YuanQiSDKConfigProperties.class)
public class YuanQiSDKConfig {

  @Bean
  public YuanQiSession openAiSession(YuanQiSDKConfigProperties properties) {

    log.info("YuanQiSDKConfigProperties: {}", properties.toString());

    // 1. 配置文件
    YuanQiConfiguration configuration = new YuanQiConfiguration();
    configuration.setApiHost(properties.getApiHost());
    configuration.setApiKey(properties.getApiKey());

    // 2. 会话工厂
    DefaultYuanQiSessionFactory factory = new DefaultYuanQiSessionFactory(configuration);

    // 3. 开启会话
    return factory.openSession();
  }

}
```

在配置yml配置当中填入参数
```yaml
yuanqi:
  sdk:
    config:
      api-host: https://yuanqi.tencent.com/openapi/
      api-key: 智能体API apikey
      assistant-id: 智能体API assistantId
      user-id: 智能体API userId
```
