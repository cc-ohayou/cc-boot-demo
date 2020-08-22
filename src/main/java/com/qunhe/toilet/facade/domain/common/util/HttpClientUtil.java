package com.qunhe.toilet.facade.domain.common.util;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//import org.apache.http.impl.client.CloseableHttpClient;

/**
 * 功能说明:http协议控制器
 * <p> 系统版本: v1.0<br>
 * 开发人员: luopeng12856
 * 开发时间: 2016—05-26 上午10:12 <br>
 */
public class HttpClientUtil {

    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final JsonNodeFactory factory = new JsonNodeFactory(false);

    /**
     * 编码格式.
     */
    private static final String CHARSET = "UTF-8";

    /**
     * HTTP HEADER字段 Authorization应填充字符串Bearer
     */
    public static final String BEARER = "Bearer ";
    /**
     * 环境
     */
    public static final String URL = "https://sandbox.hscloud.cn";
    /**
     * 环境
     */
    public static final String OPENURL = "https://open.hscloud.cn";

    /**
     * 按日期查询除权信息
     */
    public static final String EXCLUDE_RIGHTS_BY_DATE="/quote/v1/exrightsbydate";

    /**
     * 查询除权除息信息
     */
    public static final String EXCLUDE_RIGHTS_BY_CODE="/quote/v1/exrights";

    /**
     * HTTP HEADER字段 Authorization应填充字符串BASIC
     */
    public static final String BASIC = "Basic ";


    private static CloseableHttpClient httpClient = null;
    /**
     * 连接超时时间
     */
    private final static int connectTimeout = 5000;

    /**
     * socket连接超时时间
     */
    private final static int socketTimeout = 20000;

    /**
     * 发送请求相应时间
     */
    private final static int requestTimeout = 15000;
    /*
     * 允许管理器限制最大连接数 ，还允许每个路由器针对某个主机限制最大连接数。
     */
    private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    /**
     * 最大连接数
     */
    private final static int MAX_TOTAL_CONNECTIONS = 500;
    /**
     * 每个路由最大连接数 访问每个目标机器 算一个路由 默认 2个
     */
    private final static int MAX_ROUTE_CONNECTIONS = 80;

    static {
        cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);// 设置最大路由数
        cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);// 最大连接数

        /**
         * 大量的构造器设计模式，很多的配置都不建议直接new出来，而且相关的API也有所改动，例如连接参数，
         * 以前是直接new出HttpConnectionParams对象后通过set方法逐一设置属性， 现在有了构造器，可以通过如下方式进行构造：
         * SocketConfig.custom().setSoTimeout(100000).build();
         */
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true)
                .build();
        cm.setDefaultSocketConfig(socketConfig);
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.BEST_MATCH)
                .setExpectContinueEnabled(true)
                .setStaleConnectionCheckEnabled(true).setRedirectsEnabled(true)
                .build();
        // CodingErrorAction.IGNORE指示通过删除错误输入、向输出缓冲区追加 coder
        // 的替换值和恢复编码操作来处理编码错误的操作。
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setCharset(Consts.UTF_8)
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE).build();
        httpClient = HttpClients.custom().setConnectionManager(cm)
                .setDefaultRequestConfig(defaultRequestConfig)
                .setDefaultConnectionConfig(connectionConfig).build();
    }
    public static String sendPost(String url, Map<String, String> params,Map<String, String> headerMap, HttpHost proxy) {
        try {
            HttpPost post = new HttpPost(url);
            initPostRequestConfig(post,proxy);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
//            post.setHeader("content-type", "application/json");

            List<NameValuePair> npList = organizeParamsAsNamePair(params);
            post.setEntity(new UrlEncodedFormEntity(npList, CHARSET));
            HttpResponse response = httpClient.execute(post);
            int status = response.getStatusLine().getStatusCode();
            HttpEntity entity = null;
            try {
                entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, CHARSET);
                    LogUtils.log("result = " + result, log);
                    return result;
                }else {
                    return null;
                }

            } catch (Exception e) {
                LogUtils.error("HttpClient   请求 http状态码 status = [" + status
                        + "]  获取HttpEntity ", e, log);
                throw new RuntimeException(e);
            } finally {
                if (entity != null) {
                    entity.getContent().close();
                }
            }
        } catch (ClientProtocolException e) {
            LogUtils.log("HttpClient   请求  ClientProtocolException ", e, log);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LogUtils.log("HttpClient   请求  IOException ", e, log);
            throw new RuntimeException(e);
        }
    }


    public static String sendApplicationJsonPost(HttpPost post, HttpHost proxy) {
        try {
            initPostRequestConfig(post,proxy);
            HttpResponse response = httpClient.execute(post);
            int status = response.getStatusLine().getStatusCode();
            HttpEntity entity = null;
            try {
                entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, CHARSET);
                    LogUtils.log("result = " + result, log);
                    return result;
                }else {
                    return null;
                }

            } catch (Exception e) {
                LogUtils.error("HttpClient   请求 http状态码 status = [" + status
                        + "]  获取HttpEntity ", e, log);
                throw new RuntimeException(e);
            } finally {
                if (entity != null) {
                    entity.getContent().close();
                }
            }
        } catch (ClientProtocolException e) {
            LogUtils.log("HttpClient   请求  ClientProtocolException ", e, log);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LogUtils.log("HttpClient   请求  IOException ", e, log);
            throw new RuntimeException(e);
        }
    }
    private static List<NameValuePair> organizeParamsAsNamePair(Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if (params != null) {
            int n = 0;
            for (Entry<String, String> set : params.entrySet()) {
                if (n == 0) {
                    n++;
                    sb.append(set.getKey()).append("=").append(set.getValue());
                } else {
                    sb.append("&").append(set.getKey()).append("=").append(set.getValue());
                }
                nvps.add(new BasicNameValuePair(set.getKey(), set.getValue()));
            }
        }
        return nvps;
    }

    private static void initPostRequestConfig(HttpPost post,HttpHost proxy) {
        RequestConfig.Builder builder = RequestConfig.custom();
        if (proxy != null) {
            builder.setProxy(proxy);
            RequestConfig requestConfig = builder
                    .setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout)
                    .setConnectionRequestTimeout(requestTimeout)
                    .setExpectContinueEnabled(false)
                    .setRedirectsEnabled(true).build();
            post.setConfig(requestConfig);
        }
    }

    /**
     * get请求
     *
     * @param url
     * @param params
     * @param charSet
     * @return
     */
    public static String sendGet(String url, Map<String, String> params,
                                 String charSet, HttpHost proxy, String authorization,
                                 String interfacename) {
        try {
            StringBuffer urlbuf = new StringBuffer(url);
            if (params != null) {
                int n = 0;
                for (Entry<String, String> set : params.entrySet()) {
                    if (!urlbuf.toString().contains("?")) {
                        urlbuf.append("?");
                    }
                    if (n != 0) {
                        urlbuf.append("&");
                    }
                    urlbuf.append(set.getKey()).append("=")
                            .append(set.getValue());
                    n++;
                }
            }
//            LogUtils.log("\n功能名称：" + interfacename + "\n" + "post  url = [" + urlbuf.toString() + "]", log);
            HttpGet get = new HttpGet(urlbuf.toString());
            get.setHeader("Content-Type", "application/x-www-form-urlencoded");
            get.setHeader("Authorization", authorization);
            // HttpUriRequest get = new HttpGet(urlbuf.toString());
            RequestConfig.Builder builder = RequestConfig.custom();
            if (proxy != null) {
                builder.setProxy(proxy);
            }

            RequestConfig defaultConfig = builder
                    .setSocketTimeout(socketTimeout)
                    .setConnectTimeout(connectTimeout)
                    .setConnectionRequestTimeout(requestTimeout)
                    .setExpectContinueEnabled(false).setRedirectsEnabled(true)
                    .build();
            get.setConfig(defaultConfig);

            HttpResponse response = httpClient.execute(get);

            int status = response.getStatusLine().getStatusCode();
            HttpEntity entity = null;
            try {
                entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, charSet);

                    if (result.startsWith("Error")) {
                        log.error("url is :" + urlbuf.toString());
                        log.error("result:" + result);
                        log.error("status:" + status);
                    }
//                    LogUtils.log("result = " + result, log);
                    return result;

                } else {
                    return null;
                }

            } catch (Exception e) {
                LogUtils.error("HttpClient   请求 http状态码 status = [" + status
                        + "]  ", e, log);
                throw new RuntimeException(e);
            } finally {
                if (entity != null) {
                    entity.getContent().close();
                }
            }
        } catch (ClientProtocolException e) {
            LogUtils.error("HttpClient   请求  ClientProtocolException url=" + url + ",params=" + params, e, log);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LogUtils.error("HttpClient   请求  IOException url=" + url + ",params=" + params, e, log);
            throw new RuntimeException(e);
        }

    }

    public static String sendPost(String url, Map<String, String> params,
                                  String charSet, String charsetReturn, HttpHost proxy,
                                  String authorization, String interfacename) {
        try {
            HttpPost post = new HttpPost(url);
            RequestConfig.Builder builder = RequestConfig.custom();
            if (proxy != null) {
                builder.setProxy(proxy);
                RequestConfig requestConfig = builder
                        .setSocketTimeout(socketTimeout)
                        .setConnectTimeout(connectTimeout)
                        .setConnectionRequestTimeout(requestTimeout)
                        .setExpectContinueEnabled(false)
                        .setRedirectsEnabled(true).build();
                post.setConfig(requestConfig);
            }

            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            post.setHeader("Authorization", authorization);
            List<NameValuePair> nvps = new ArrayList<>();
            StringBuffer sb = new StringBuffer();
            if (params != null) {
                int n = 0;
                for (Entry<String, String> set : params.entrySet()) {
                    if (n == 0) {
                        n++;
                        sb.append(set.getKey() + "=" + set.getValue());
                    } else {
                        sb.append("&" + set.getKey() + "=" + set.getValue());
                    }
                    nvps.add(new BasicNameValuePair(set.getKey(), set
                            .getValue()));
                }
            }
            post.setEntity(new UrlEncodedFormEntity(nvps, charSet));
            LogUtils.log("\n功能名称：" + interfacename + "\n" + "post  url = ["
                    + (url.endsWith("?") ? url : url + "?") + sb.toString()
                    + "]", log);

            HttpResponse response = httpClient.execute(post);
            int status = response.getStatusLine().getStatusCode();
            HttpEntity entity = null;
            try {
                entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, charsetReturn);
                    LogUtils.log("result = " + result, log);
                    return result;
                } else {
                    return null;
                }

            } catch (Exception e) {
                LogUtils.log("HttpClient   请求 http状态码 status = [" + status
                        + "]  获取HttpEntity ", e, log);
                throw new RuntimeException(e);
            } finally {
                if (entity != null) {
                    entity.getContent().close();
                }
            }
        } catch (ClientProtocolException e) {
            LogUtils.log("HttpClient   请求  ClientProtocolException ", e, log);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LogUtils.log("HttpClient   请求  IOException ", e, log);
            throw new RuntimeException(e);
        }
    }


    /**
     * cifangf 是对"App Key:App Secret"进行 Base64 编码后的字符串（区分大小写，包含冒号，但不包含双引号,采用
     * UTF-8 编码）。 例如: Authorization: Basic eHh4LUtleS14eHg6eHh4LXNlY3JldC14eHg=
     * 其中App Key和App Secret可在开放平台上创建应用后获取。
     */
    public static String Base64(String appkey, String appsecret, String basic) throws UnsupportedEncodingException {
        String str = appkey + ":" + appsecret;
        byte[] encodeBase64 = Base64.encodeBase64(str
                .getBytes(HttpClientUtil.CHARSET));
        LogUtils.log("\n功能名称：AppKey:AppSecret Base64编码" + "\n" + new String(encodeBase64), log);
        return basic + new String(encodeBase64);
    }

    public static class LogUtils {

        public static void log(String msg, Logger log) {
            /*System.out.println(msg);*/
            log.info(msg);
        }

        public static void log(String msg, Exception e, Logger log) {
			/*System.out.println(msg + " 异常 message = [" + e.getMessage() + "]");*/
            //log.info(msg + " 异常 message = [" + e.getMessage() + "]", e);
        }

        public static void error(String msg, Exception e, Logger log) {
			/*System.out.println(msg + " 异常 message = [" + e.getMessage() + "]");*/
            log.error(msg + " 异常 message = [" + e.getMessage() + "]", e);
        }

    }
    public static String getIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");
        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded;
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                ip = realIp + "/" + forwarded.replaceAll(", " + realIp, "");
            }
        }
        return ip;
    }

    public static ObjectNode sendHTTPSRequest(java.net.URL url, Object dataBody, String method) {

//        CloseableHttpClient httpClient = credential.client;
        ObjectNode resObjectNode = factory.objectNode();
        CloseableHttpResponse response = null;
        try {
            if (method.equals(HttpMethod.POST)) {
                HttpPost httpPost = new HttpPost(url.toURI());

                httpPost.setEntity(new StringEntity(dataBody.toString(), "UTF-8"));
                response = httpClient.execute(httpPost);
            } else if (method.equals(HttpMethod.PUT)) {
                HttpPut httpPut = new HttpPut(url.toURI());

                httpPut.setEntity(new StringEntity(dataBody.toString(), "UTF-8"));
                response = httpClient.execute(httpPut);
            } else if (method.equals(HttpMethod.GET)) {

                HttpGet httpGet = new HttpGet(url.toURI());

                response = httpClient.execute(httpGet);

            } else if (method.equals(HttpMethod.DELETE)) {
                HttpDelete httpDelete = new HttpDelete(url.toURI());

                response = httpClient.execute(httpDelete);
            }

            if (null != response) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    String responseContent = EntityUtils.toString(entity, "UTF-8");
                    EntityUtils.consume(entity);

                    ObjectMapper mapper = new ObjectMapper();
                    JsonFactory factory = mapper.getJsonFactory();
                    JsonParser jp = factory.createJsonParser(responseContent);

                    resObjectNode = mapper.readTree(jp);
                    resObjectNode.put("statusCode", response.getStatusLine().getStatusCode());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
           /* try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

        return resObjectNode;
    }

    public static CloseableHttpClient getCloseableClient() {
        return httpClient;
    }

}
