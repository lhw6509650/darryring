package com.darryring.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;


public class HttpClientUtil {

    public static boolean postJson(String url, String body, String charset) {

        boolean result = false;
        if (null == charset) {
            charset = "UTF-8";
        }
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        try {
            httpClient = HttpConnectionManager.getInstance().getHttpClient();
            httpPost = new HttpPost(url);

            // 设置连接超时,设置读取超时
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000)
                    .setSocketTimeout(10000)
                    .build();
            httpPost.setConfig(requestConfig);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json;charset=utf-8");

            // 设置参数
            StringEntity se = new StringEntity(body, "UTF-8");
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result=true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
