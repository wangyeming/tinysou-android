# tinysou-android-sdk

=======

想在项目中学习，查看[demo](https://github.com/wangyeming/tinysou-android-sdk-demo/tree/master)

##介绍
微搜索android-sdk，通过调用public接口，实现发送微搜索请求、接收处理微搜索响应等功能。目前支持的接口有：
* 搜索API
* 自动补全API

## 如何使用
* 导入tinysou-android-sdk-v1.00.jar到android project的libs/目录中
* Android studio中，鼠标右击jar文件，选择Add As Library

## 如何在应用中进行微搜索
* 搜索API
  
  定义你的微搜索的engine_key
```java
    String engine_key = "示例key";
```
  发送搜索请求，获得搜索结果
```java
    //建立微搜索主机
    TinySouClient client = new TinySouClient(engine_key);
    //设置搜索结果来自于第几页
    client.setPage(searchPage);
    //获得String格式的搜索结果
    String result = client.Search(search_content);
```

* 自动补全API

  定义你的微搜索的engine_key（同上）<br\>
  发送自动补全请求，获得自动补全结果
```java
    //建立微搜索主机
    TinySouClient client = new TinySouClient(engine_key);
    //获得String格式的自动补全结果
    String result = client.AutoSearch(search_content);
```
## 如何获取搜索结果中指定标签的值
tinysou-android-sdk提供使用fastjson获得json格式的搜索结果的方法。<br\>
例如，我们想获得搜索返回结果records第一个结果的document的title：
```java
    //将String搜索结果转化为Json格式
    TinySouJsonHelp tinySouJsonHelp = JSON.parseObject(content, TinySouJsonHelp.class);
    //获取搜索结果指定项的值
    String firstTitle = tinySouJsonHelp.getRecords().get(0).getDocument().getTitle();
```
其他参数的获取方法请参考API文档。
