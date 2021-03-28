package com.terraboxstudios.publicsms.utils;

import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtility {

    public static HttpURLConnection sendGetRequest(String requestURL)
            throws IOException {
        URL url = new URL(requestURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);

        httpConn.setDoInput(true);
        httpConn.setDoOutput(false);

        return httpConn;
    }

    public static HttpURLConnection sendPostRequest(String requestURL,
                                                    Map<String, String> params) throws IOException {
        URL url = new URL(requestURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);

        httpConn.setDoInput(true);

        StringBuilder requestParams = new StringBuilder();

        if (params != null && params.size() > 0) {

            httpConn.setDoOutput(true);

            for (String key : params.keySet()) {
                String value = params.get(key);
                requestParams.append(URLEncoder.encode(key, "UTF-8"));
                requestParams.append("=").append(
                        URLEncoder.encode(value, "UTF-8"));
                requestParams.append("&");
            }

            OutputStreamWriter writer = new OutputStreamWriter(
                    httpConn.getOutputStream());
            writer.write(requestParams.toString());
            writer.flush();
        }

        return httpConn;
    }

    public static HttpURLConnection sendPostRequest(String requestURL,
                                                    JsonObject data) throws IOException {
        URL url = new URL(requestURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);

        httpConn.setDoInput(true);

        if (data != null && data.size() > 0) {

            httpConn.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(
                    httpConn.getOutputStream());
            writer.write(data.toString());
            writer.flush();
        }

        return httpConn;
    }

    public static String readSingleLineRespone(HttpURLConnection httpConn) throws IOException {
        InputStream inputStream;
        if (httpConn != null) {
            inputStream = httpConn.getInputStream();
        } else {
            throw new IOException("Connection is not established.");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));

        String response = reader.readLine();
        reader.close();

        return response;
    }

    public static String[] readMultipleLinesRespone(HttpURLConnection httpConn) throws IOException {
        InputStream inputStream;
        if (httpConn != null) {
            inputStream = httpConn.getInputStream();
        } else {
            throw new IOException("Connection is not established.");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        List<String> response = new ArrayList<String>();

        String line = "";
        while ((line = reader.readLine()) != null) {
            response.add(line);
        }
        reader.close();

        return response.toArray(new String[0]);
    }

}