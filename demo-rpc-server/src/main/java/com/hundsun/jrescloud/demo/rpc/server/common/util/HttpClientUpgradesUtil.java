package com.hundsun.jrescloud.demo.rpc.server.common.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

/**
 * HTTP客户端 引用httpclient4.5.1版本jar包
 */
public class HttpClientUpgradesUtil {

    private static final String HTTP_CONTENT_CHARSET = "UTF-8";
    /** 最大连接时间 */
    public static final int MAX_TIME_OUT = 10000;
    /** 最大闲置时间 */
    public static final int MAX_IDLE_TIME_OUT = 60000;
    /** 最大连接数 */
    public static final int MAX_CONN = 100;

    private static CloseableHttpClient httpClient = null;

    /** 配置请求超时时间 */
    private static RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(MAX_TIME_OUT) // 从连接池获取连接的timeout
            .setConnectTimeout(MAX_TIME_OUT) // 和服务器建立连接的timeout
            .setSocketTimeout(MAX_TIME_OUT) // 从服务器读取数据的timeout
            .build();

    /**
     * 
     * 获取httpClient实例
     * 
     */
    public synchronized static HttpClient getHttpClient() {
        if (httpClient == null) {
            initHttpClient();
        }
        return httpClient;
    }

    /**
     * 
     * 初始化httpClient
     * 
     */
    public synchronized static void initHttpClient() {
        if (httpClient == null) {
            ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
            LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create().register(
                    "http", plainsf).register("https", sslsf).build();
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
            // 将最大连接数增加到5000
            cm.setMaxTotal(MAX_CONN * 5);
            // 将每个路由基础的连接增加到1000
            cm.setDefaultMaxPerRoute(MAX_CONN * 2);

            httpClient = HttpClients.custom().setConnectionManager(cm).build();

        }
    }

    /**
     * 
     * 发送GET请求
     * 
     * @param url
     *            服务地址
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static String executeGET(String url) throws Exception {
        return executeGET(url, null);
    }

    /**
     * 
     * 发送GET请求
     * 
     * @param url
     *            服务地址
     * @param headers
     *            requestHeader信息
     * @return
     * @throws IOException
     * @throws HttpException
     */
    public static String executeGET(String url, List<Header> headers) throws Exception {
        HttpGet get = new HttpGet(url);
        Object result = execute(get, headers, false);
        if (result != null) {
            return result.toString();
        } else {
            return "";
        }
    }

    /**
     * 
     * 发送GET请求
     * 
     * @param url
     *            服务地址
     * @param headers
     *            requestHeader信息
     * @param returnResponse
     *            是否返回Response，true:返回HttpResponse对象，false：返回消息体字符串
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static Object executeGET(String url, List<Header> headers, boolean returnResponse) throws Exception {
        HttpGet get = new HttpGet(url);
        return execute(get, headers, returnResponse);
    }

    /**
     * 
     * 发送POST请求
     * 
     * @param url
     *            服务地址
     * @param params
     *            http请求参数
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static String executePOST(String url, List<NameValuePair> params) throws Exception {
        return executePOST(url, params, null);
    }

    /**
     * 
     * 发送POST请求
     * 
     * @param url
     *            服务地址
     * @param params
     *            http请求参数
     * @param headers
     *            requestHeader信息
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static String executePOST(String url, List<NameValuePair> params, List<Header> headers) throws Exception {
        HttpPost post = new HttpPost(url);
        if (CollectionUtil.isNotEmpty(params)) {
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params, HTTP_CONTENT_CHARSET);
            post.setEntity(uefEntity);
        }
        Object result = execute(post, headers, false);
        if (result != null) {
            return result.toString();
        } else {
            return "";
        }
    }

    /**
     * 
     * 发送POST请求
     * 
     * @param url
     *            服务地址
     * @param params
     *            http请求参数
     * @param headers
     *            requestHeader信息
     * @param returnResponse
     *            是否返回Response，true:返回HttpResponse对象，false：返回消息体字符串
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static Object executePOST(String url, List<NameValuePair> params, List<Header> headers,
            boolean returnResponse) throws Exception {
        HttpPost post = new HttpPost(url);
        if (CollectionUtil.isNotEmpty(params)) {
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params, HTTP_CONTENT_CHARSET);
            post.setEntity(uefEntity);
        }
        return execute(post, headers, returnResponse);
    }

    /**
     * 
     * 发送POST请求，参数放在requestBody里（不含key）
     * 
     * @param url
     *            服务地址
     * @param param
     *            http请求参数
     * 
     *            {"a":"1", "b":"2" }
     * 
     * 
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static String executePOSTRequestBody(String url, String param) throws Exception {
        return executePOSTRequestBody(url, param, null);
    }

    /**
     * 
     * 发送POST请求，参数放在requestBody里（不含key）
     * 
     * @param url
     *            服务地址
     * @param param
     *            http请求参数
     * @param headers
     *            requestHeader信息
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static String executePOSTRequestBody(String url, String param, List<Header> headers) throws Exception {
        HttpPost post = new HttpPost(url);
        if (StrUtil.isNotBlank(param)) {
            post.setEntity(new StringEntity(param, HTTP_CONTENT_CHARSET));
        }
        Object result = execute(post, headers, false);
        if (result != null) {
            return result.toString();
        } else {
            return "";
        }
    }

    /**
     * 
     * 发送POST请求，参数放在requestBody里（不含key）
     * 
     * @param url
     *            服务地址
     * @param param
     *            http请求参数
     * @param headers
     *            requestHeader信息
     * @param returnResponse
     *            是否返回Response，true:返回HttpResponse对象，false：返回消息体字符串
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static Object executePOSTRequestBody(String url, String param, List<Header> headers, boolean returnResponse)
            throws Exception {
        HttpPost post = new HttpPost(url);
        if (StrUtil.isNotBlank(param)) {
            post.setEntity(new StringEntity(param, HTTP_CONTENT_CHARSET));
        }
        return execute(post, headers, returnResponse);
    }

    /**
     * 
     * 发送PUT请求
     * 
     * @param url
     *            服务地址
     * @param params
     *            http请求参数
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static String executePUT(String url, List<NameValuePair> params) throws Exception {
        return executePUT(url, params, null);
    }

    /**
     * 
     * 发送PUT请求
     * 
     * @param url
     *            服务地址
     * @param params
     *            http请求参数
     * @param headers
     *            requestHeader信息
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static String executePUT(String url, List<NameValuePair> params, List<Header> headers) throws Exception {
        HttpPut put = new HttpPut(url);
        if (CollectionUtil.isNotEmpty(params)) {
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params, HTTP_CONTENT_CHARSET);
            put.setEntity(uefEntity);
        }
        Object result = execute(put, headers, false);
        if (result != null) {
            return result.toString();
        } else {
            return "";
        }
    }

    /**
     * 
     * 发送PUT请求
     * 
     * @param url
     *            服务地址
     * @param params
     *            http请求参数
     * @param headers
     *            requestHeader信息
     * @param returnResponse
     *            是否返回Response，true:返回HttpResponse对象，false：返回消息体字符串
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static Object executePUT(String url, List<NameValuePair> params, List<Header> headers, boolean returnResponse)
            throws Exception {
        HttpPut put = new HttpPut(url);
        if (CollectionUtil.isNotEmpty(params)) {
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params, HTTP_CONTENT_CHARSET);
            put.setEntity(uefEntity);
        }
        return execute(put, headers, returnResponse);
    }

    /**
     * 
     * 发送Delete请求
     * 
     * @param url
     *            服务地址
     * @param params
     *            http请求参数
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static String executeDelete(String url, List<NameValuePair> params) throws Exception {
        return executeDelete(url, params, null);
    }

    /**
     * 
     * 发送Delete请求
     * 
     * @param url
     *            服务地址
     * @param params
     *            http请求参数
     * @param headers
     *            requestHeader信息
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static String executeDelete(String url, List<NameValuePair> params, List<Header> headers) throws Exception {
        HttpDelete delete = new HttpDelete(url);
        Object result = execute(delete, headers, false);
        if (result != null) {
            return result.toString();
        } else {
            return "";
        }
    }

    /**
     * 
     * 发送Delete请求
     * 
     * @param url
     *            服务地址
     * @param params
     *            http请求参数
     * @param headers
     *            requestHeader信息
     * @param returnResponse
     *            是否返回Response，true:返回HttpResponse对象，false：返回消息体字符串
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static Object executeDelete(String url, List<NameValuePair> params, List<Header> headers,
            boolean returnResponse) throws Exception {
        HttpDelete delete = new HttpDelete(url);
        return execute(delete, headers, returnResponse);
    }

    /**
     * 
     * 发送PATCH请求
     * 
     * 
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static String executePATCH(String url, String param) throws Exception {
        return executePATCH(url, param, null);
    }

    /**
     * 
     * 发送PATCH请求
     * 
     * @param url
     *            服务地址
     * @param param
     *            http请求参数
     * @param headers
     *            requestHeader信息
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static String executePATCH(String url, String param, List<Header> headers) throws Exception {
        HttpPatch patch = new HttpPatch(url);
        if (StrUtil.isNotBlank(param)) {
            patch.setEntity(new StringEntity(param, HTTP_CONTENT_CHARSET));
        }
        Object result = execute(patch, headers, false);
        if (result != null) {
            return result.toString();
        } else {
            return "";
        }
    }

    /**
     * 
     * 发送PATCH请求
     * 
     * @param url
     *            服务地址
     * @param param
     *            http请求参数
     * @param headers
     *            requestHeader信息
     * @param returnResponse
     *            是否返回Response，true:返回HttpResponse对象，false：返回消息体字符串
     * @return
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    public static Object executePATCH(String url, String param, List<Header> headers, boolean returnResponse)
            throws Exception {
        HttpPatch patch = new HttpPatch(url);
        if (StrUtil.isNotBlank(param)) {
            patch.setEntity(new StringEntity(param, HTTP_CONTENT_CHARSET));
        }
        return execute(patch, headers, returnResponse);
    }

    /**
     * 
     * 发送请求
     * 
     * @param httpMethod
     * @return
     * @throws SocketException
     * @throws IOException
     * @throws HttpException
     * @since Ver 3.0
     */
    private static Object execute(HttpRequestBase httpMethod, List<Header> headers, boolean returnResponse)
            throws Exception {
        // 返回报文
        Object result = null;
        try {
            httpMethod.setConfig(requestConfig);
            httpMethod.addHeader("Accept", "application/json;charset=UTF-8");
            httpMethod.addHeader("Content-Type", "application/json;charset=UTF-8");
            if (headers != null && !headers.isEmpty()) {
                for (Header header : headers) {
                    httpMethod.addHeader(header);
                }
            }
            HttpResponse response = getHttpClient().execute(httpMethod);
            if (returnResponse) {
                result = response;
            } else {
                HttpEntity entity = response.getEntity();
                if (entity != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    result = EntityUtils.toString(entity, HTTP_CONTENT_CHARSET);
                } else {
                    httpMethod.abort();
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpMethod.releaseConnection();
        }
        return result;
    }

    /**
     * 
     * 获取返回消息体
     * 
     * @param response
     * @return
     * @throws Exception
     * @since Ver 1.0
     */
    public static String getContent(HttpResponse response) throws Exception {
        String resultMsg = "";
        // 返回报文
        if (response != null) {
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    resultMsg = EntityUtils.toString(entity, HTTP_CONTENT_CHARSET);
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return resultMsg;
    }
}
