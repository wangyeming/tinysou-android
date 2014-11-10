package Help;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by tinysou on 14-9-22.
 * Author:Yeming Wang
 * Data: 2014.10.11
 */
public class HttpHelp {
    // --------------------------------------变量----------------------------------------------------
    public final String HTTP_GET = "GET";
    public final String HTTP_POST = "POST";
    public final String HTTP_PUT = "PUT";
    public final String HTTP_DELETE = "DELETE";
    // 当前请求的url
    protected String url = new String();
    // HTTP请求类型
    protected String requestType = HTTP_POST;
    // 连接请求的超时时间
    protected int connectionTimeout = 5000;
    // 读取远程数据的超时时间
    protected int soTimeout = 10000;
    // 服务端返回的状态码
    protected int statusCode = -1;
    // 当前链接的字符编码
    protected String charset = HTTP.UTF_8;
    // HTTP 请求管理器
    protected HttpRequestBase httpRequest = new HttpRequestBase() {
        @Override
        public String getMethod() {
            return null;
        }
    };
    // HTTP 请求的配置参数
    protected HttpParams httpParameters = null;
    // HTTP 请求响应
    protected HttpResponse httpResponse = null;
    // HTTP 客户端连接管理器
    protected HttpClient httpClient = null;
    // 绑定 HTTP 请求的事件监听器
    protected OnHttpRequestListener onHttpRequestListener = null;
    // 异常信息
    protected String exceptionMessage;

    public HttpHelp() {
    }

    public HttpHelp(OnHttpRequestListener listener) {
        this.setOnHttpRequestListener(listener);
    }

    // 设置当前请求的url
    public HttpHelp setUrl(String url) {
        this.url = url;
        return this;
    }

    // 获取当前Url
    public String getUrl() {
        return this.url;
    }

    // 设置请求超时时间
    public HttpHelp setConnectedTimeout(int timeout) {
        this.connectionTimeout = timeout;
        return this;
    }

    // 获取请求超时时间
    public int getConnectedTimeout() {
        return this.connectionTimeout;
    }

    // 设置远程数据读取超时时间
    public HttpHelp setSoTimeout(int timeout) {
        this.soTimeout = timeout;
        return this;
    }

    // 获取远程数据读取超时时间
    public int getSoTimeout() {
        return this.soTimeout;
    }

    // 设置获取内容的编码格式
    public HttpHelp setCharset(String Charset) {
        this.charset = Charset;
        return this;
    }

    // 获取 获取内容的编码格式
    public String getCharset() {
        return this.charset;
    }

    // 设置http请求类型
    public void setRequestType(String type) {
        type = type.toLowerCase();
        if (type.equals("get")) {
            this.requestType = HTTP_GET;
        } else if (type.equals("post")) {
            this.requestType = HTTP_POST;
        } else if (type.equals("put")) {
            this.requestType = HTTP_PUT;
        } else if (type.equals("delete")) {
            this.requestType = HTTP_DELETE;
        } else {
            return;
        }
    }

    // 获取当前http请求类型
    public String getRequestType() {
        return this.requestType;
    }

    // 判断是否为get请求
    public boolean isGet() {
        return this.requestType == HTTP_GET;
    }

    // 判断是否为post请求
    public boolean isPost() {
        return this.requestType == HTTP_POST;
    }

    // 判断是否为put请求
    public boolean isPut() {
        return this.requestType == HTTP_PUT;
    }

    // 判断是否为delete请求
    public boolean isDelete() {
        return this.requestType == HTTP_DELETE;
    }

    // 获取HTTP请求响应信息
    public HttpResponse getHttpResponse() {
        return this.httpResponse;
    }

    // 获取HTTP客户端连接管理器
    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    // 获取HTTP 控制器
    public HttpRequestBase getHttpRequest() {
        return this.httpRequest;
    }

    // 获取HTTP GET控制器
    public HttpGet getHttpGet() {
        return (HttpGet) this.httpRequest;
    }

    // 获取HTTP POST控制器
    public HttpPost getHttpPost() {
        return (HttpPost) this.httpRequest;
    }

    // 获取HTTP PUT控制器
    public HttpPut getHttpPut() {
        return (HttpPut) this.httpRequest;
    }

    // 获取HTTP DELETE控制器
    public HttpDelete getHttpDelete() {
        return (HttpDelete) this.httpRequest;
    }

    // 获取请求的状态码
    public int getStatusCode() {
        return this.statusCode;
    }

    // 设置header--String
    public HttpHelp addHeader(String name, String value) {
        this.httpRequest.addHeader(name, value);
        return this;
    }

    // 设置header--map
    public HttpHelp setHeader(Header header) {
        this.httpRequest.setHeader(header);
        return this;
    }

    // 获取header
    public Header[] getAllHeader() {
        return httpRequest.getAllHeaders();
    }

    // 获取First header
    public Header getFirstHeader(String name) {
        return httpRequest.getFirstHeader(name);
    }

    // 发送get请求
    public String get(String url) throws Exception {
        this.requestType = HTTP_GET;
        this.setUrl(url);
        this.httpRequest = new HttpGet(url);
        this.httpClientExecute();// 执行客户端请求
        return this.checkStatus();
    }

    // 发送post请求
    public String post(String url) throws Exception {
        this.requestType = HTTP_POST;
        this.setUrl(url);
        this.httpRequest = new HttpPost(url);
        this.httpClientExecute();// 执行客户端请求
        return this.checkStatus();
    }

    // 发送put请求
    public String put(String url) throws Exception {
        this.requestType = HTTP_PUT;
        this.setUrl(url);
        this.httpRequest = new HttpPut(url);
        this.httpClientExecute();
        return this.checkStatus();
    }

    // 发送delete请求
    public String delete(String url) throws Exception {
        this.requestType = HTTP_DELETE;
        this.setUrl(url);
        this.httpRequest = new HttpDelete(url);
        this.httpClientExecute();
        return this.checkStatus();
    }

    // 设置post请求的entity
    public void buildPostEntity(StringEntity entity) {
        this.getHttpPost().setEntity(entity);
    }

    // 设置put请求的entity
    public void buildPutEntity(StringEntity entity) {
        this.getHttpPut().setEntity(entity);
    }

    // 设置put请求的entity
    public void buildRequestEntity(String method, StringEntity entity) {
        if (method == "POST") {
            buildPostEntity(entity);
        } else if (method == "put") {
            buildPutEntity(entity);
        } else {
            return;
        }
    }

    // 执行http请求
    public void httpClientExecute() throws Exception {
        this.httpParameters = new BasicHttpParams();// 配置http请求参数
        this.httpParameters.setParameter("charset", this.charset);// 指定http请求参数的字符编码
        // 设置连接请求超时时间
        HttpConnectionParams.setConnectionTimeout(this.httpParameters,
                this.connectionTimeout);
        // 设置socket请求超时时间
        HttpConnectionParams.setSoTimeout(this.httpParameters, this.soTimeout);
        // 创建一个默认的http请求客户端
        try {
            this.httpClient = new DefaultHttpClient(this.httpParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 执行 HTTP POST请求执行前的事件监听回调工作（如：自定义提交的数据字段或上传的文件等）
        this.getOnHttpRequestListener().onRequest(this);
        // 发送http请求并获取服务端响应状态
        try {
            this.httpResponse = this.httpClient.execute(this.httpRequest);
        } catch (ConnectTimeoutException e) {
            exceptionMessage = "连接超时：" + e.getMessage();
            System.out.println("<-------ConnectTimeoutException------->");
            e.printStackTrace();
            System.out.println("<-------Exception end------->");
        } catch (SocketTimeoutException e) {
            exceptionMessage = "socket连接超时：" + e.getMessage();
            System.out.println("<-------SocketTimeoutException------->");
            e.printStackTrace();
            System.out.println("<-------Exception end------->");
        } catch (IOException e) {
            exceptionMessage = "IO异常：" + e.getMessage();
            System.out.println("<-------IOException------->");
            e.printStackTrace();
            System.out.println("<-------Exception end------->");
        } catch (Exception e) {
            exceptionMessage = "异常：：" + e.getMessage();
            System.out.println("<-------Exception------->");
            e.printStackTrace();
            System.out.println("<-------Exception end------->");
        }
        // 获取请求返回的状态码
        this.statusCode = this.httpResponse.getStatusLine().getStatusCode();

    }

    // 读取服务端返回的输入流Json并转换成TinySoHelp格式返回
    public String getInputStreamJson() throws Exception {
        HttpEntity entity = this.httpResponse.getEntity();
        // JSONObject json = new JSONObject();
        String inString = new String();
        if (entity != null) {
            // InputStreamReader reader = new
            // InputStreamReader(entity.getContent());
            // String inString = EntityUtils.toString(entity,HTTP.UTF_8);
            // System.out.println("sds "+ inString);
            // json = new JSONObject(new JSONTokener(reader) );
            inString = EntityUtils.toString(entity, HTTP.UTF_8);
        }
        return inString;
    }

    // 关闭管理器释放资源
    protected void shutdownHttpClient() {
        if (this.httpClient != null
                && this.httpClient.getConnectionManager() != null) {
            this.httpClient.getConnectionManager().shutdown();
        }
    }

    // 监听服务端响应事件并返回服务端内容
    public String checkStatus() throws Exception {
        OnHttpRequestListener listener = this.getOnHttpRequestListener();
        String content;
        // System.out.println(HttpStatus.SC_OK);
        if (this.statusCode < 400) {
            content = listener.onSucceed(this.statusCode, this);
        } else {
            content = listener.onFailed(this.statusCode, this);
        }
        this.shutdownHttpClient();
        return content;
    }

    // HTTP 请求操作时的事件监听接口
    public interface OnHttpRequestListener {
        public void onRequest(HttpHelp request) throws Exception;

        public String onSucceed(int statusCode, HttpHelp request)
                throws Exception;

        public String onFailed(int statusCode, HttpHelp request)
                throws Exception;
    }

    // 绑定 HTTP 请求的监听事件
    public HttpHelp setOnHttpRequestListener(OnHttpRequestListener listener) {
        this.onHttpRequestListener = listener;
        return this;
    }

    // 获取已绑定过的 HTTP 请求监听事件
    public OnHttpRequestListener getOnHttpRequestListener() {
        return this.onHttpRequestListener;
    }
}
