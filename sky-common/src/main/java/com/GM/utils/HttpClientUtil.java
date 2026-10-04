package com.GM.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Slf4j
public class HttpClientUtil {

    public static String doGet(String url, Map<String, String> params) {
        try {
            StringBuilder urlBuilder = new StringBuilder(url);
            if (params != null && !params.isEmpty()) {
                urlBuilder.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    urlBuilder.append(entry.getKey())
                               .append("=")
                               .append(entry.getValue())
                               .append("&");
                }
                urlBuilder.deleteCharAt(urlBuilder.length() - 1);
            }

            URL requestUrl = new URL(urlBuilder.toString());
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "UTF-8")
                );
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                log.info("HTTP GET 请求成功：url={}", url);
                return response.toString();
            } else {
                log.error("HTTP GET 请求失败：url={}, 响应码={}", url, responseCode);
                return null;
            }
        } catch (Exception e) {
            log.error("HTTP GET 请求异常：url={}", url, e);
            return null;
        }
    }
}
