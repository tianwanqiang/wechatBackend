package com.twq.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Map;

public class HttpClientUtil {

    private static Logger log = CSLog.get(HttpClientUtil.class);


    public static String doPostJson(String reqUrl, String bodyJsonData) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(reqUrl);
        StringEntity myEntity = new StringEntity(bodyJsonData, ContentType.APPLICATION_JSON);
        post.setEntity(myEntity);      // 设置请求
        Map<String, String> map = JSON.parseObject(bodyJsonData, new TypeReference<Map<String, String>>() {

        });
        String userId = map.get(Nodes.userId);
        String apiId = map.get(Nodes.apiId);
        String token = map.get(Nodes.token);
        String signature = map.get(Nodes.signature);
        post.setHeader(Nodes.token, token);
        post.setHeader(Nodes.userId, userId);
        post.setHeader(Nodes.signature, signature);
        String responseContent = null; // 响应内容
        CloseableHttpResponse response = null;
        try {
            HttpEntity he = post.getEntity();
            CSLog.debug(log, "请求地址 : " + reqUrl);
            CSLog.debug(log, "发出请求 : " + EntityUtils.toString(he, HTTP.UTF_8));
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                responseContent = paseResponse(response);
            }
            CSLog.debug(log, "收到响应 : \n" + responseContent);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (client != null)
                        client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseContent;
    }

    private static String paseResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        String body = null;
        try {
            body = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;

    }


}
