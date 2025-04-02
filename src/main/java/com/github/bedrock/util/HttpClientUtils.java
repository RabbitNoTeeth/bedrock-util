package com.github.bedrock.util;


import com.github.bedrock.util.exception.HttpException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;
import java.util.Map;
import java.util.function.Consumer;

public class HttpClientUtils {

    private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 60000;
    private static final int DEFAULT_SOCKET_TIMEOUT = 60000;

    public static HttpResponse get(String url, Map<String, Object> queryParams, Map<String, String> headers) throws Exception {
        return get(url, queryParams, headers, null);
    }

    public static HttpResponse get(String url, Map<String, Object> queryParams, Map<String, String> headers, Consumer<RequestConfig.Builder> requestConfigConsumer) throws HttpException {
        CloseableHttpClient httpclient = HttpClients.custom().build();
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (queryParams != null) {
                for (Map.Entry<String, Object> queryParam : queryParams.entrySet()) {
                    uriBuilder.addParameter(queryParam.getKey(), queryParam.getValue().toString());
                }
            }
            URI uri = uriBuilder.build();
            HttpHost target = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
            HttpGet request = new HttpGet(uri);
            RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT).setSocketTimeout(DEFAULT_SOCKET_TIMEOUT);

            if (requestConfigConsumer != null) {
                requestConfigConsumer.accept(requestConfigBuilder);
            }
            request.setConfig(requestConfigBuilder.build());
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    request.addHeader(header.getKey(), header.getValue());
                }
            }
            return httpclient.execute(target, request);
        } catch (Throwable e) {
            throw new HttpException(e);
        }
    }

    public static HttpResponse post(String url, HttpEntity entity, Map<String, String> headers) throws HttpException {
        return post(url, entity, headers, null);
    }

    public static HttpResponse post(String url, HttpEntity entity, Map<String, String> headers, Consumer<RequestConfig.Builder> requestConfigConsumer) throws HttpException {
        CloseableHttpClient httpclient = HttpClients.custom().build();
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            URI uri = uriBuilder.build();
            HttpHost target = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
            HttpPost request = new HttpPost(uri);
            request.setEntity(entity);
            RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT).setSocketTimeout(DEFAULT_SOCKET_TIMEOUT);

            if (requestConfigConsumer != null) {
                requestConfigConsumer.accept(requestConfigBuilder);
            }
            request.setConfig(requestConfigBuilder.build());
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    request.addHeader(header.getKey(), header.getValue());
                }
            }
            return httpclient.execute(target, request);
        } catch (Throwable e) {
            throw new HttpException(e);
        }
    }

    public static HttpResponse proxyGet(String proxyIp, int proxyPort, String url, Map<String, Object> queryParams, Map<String, String> headers) throws HttpException {
        HttpHost proxy = new HttpHost(proxyIp, proxyPort);
        return get(url, queryParams, headers, requestConfigBuilder -> requestConfigBuilder.setProxy(proxy));
    }

}
