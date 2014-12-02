# tinysou-android-library

=======

想在项目中学习，查看[demo](https://github.com/wangyeming/tinysou-android-sdk-demo/tree/master)

##介绍
微搜索android-library，通过调用public接口，实现发送微搜索请求、接收处理微搜索响应等功能。目前支持的接口有：
* 搜索API
* 自动补全API

## 如何使用
* 导入tinysou-android-library-v1.01.jar到android project的libs/目录中
* Android studio中，鼠标右击jar文件，选择Add As Library

## 如何在应用中进行微搜索
* 搜索API
  
  定义你的微搜索的engine_key
```java
    String engineKey = "示例key";
```
  发送搜索请求，获得搜索结果
```java
    //建立微搜索主机
    TinySouClient client = new TinySouClient(engineKey);
    //设置搜索结果来自于第几页
    client.setPage(searchPage);
    //获得String格式的搜索结果
    String result = client.Search(searchContent);
```

* 自动补全API

  定义你的微搜索的engine_key（同上）<br\>
  发送自动补全请求，获得自动补全结果
```java
    //建立微搜索主机
    TinySouClient client = new TinySouClient(engineKey);
    //设置请求参数
    JSONObject json = new JSONObject( " {\"per_page\":10,\"engine_key\":\"0b732cc0ea3c11874190\",\"page\":1,\"c\":\"page\",\"q\":\"搜索\"}")
    client.setSearchParams(json);
    //获得String格式的自动补全结果
    String result = client.AutoSearch(searchContent);
```
## 如何获取搜索结果中指定标签的值
tinysou-android-library提供使用fastjson获得json格式的搜索结果的方法。<br\>
例如，我们想获得搜索返回结果records第一个结果的document的title：
```java
    //将String搜索结果转化为Json格式
    TinySouJsonHelp tinySouJsonHelp = JSON.parseObject(content, TinySouJsonHelp.class);
    设置自动补全参数
    JSONObject json = new JSONObject( " {\"per_page\":10,\"engine_key\":\"0b732cc0ea3c11874190\",\"page\":1,\"c\":\"page\",\"q\":\"搜索\"}")
    client.setAcParams(json);
    //获取搜索结果指定项的值
    String firstTitle = tinySouJsonHelp.records.get(0).document.title;
```
其他参数的获取方法请参考API文档。
