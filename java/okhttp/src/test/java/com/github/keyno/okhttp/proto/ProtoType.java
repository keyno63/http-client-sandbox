package com.github.keyno.okhttp.proto;

import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ProtoType {

    private static String requestUrl = "http://localhost:8080/hello";

    @Test
    void test_get() throws Exception {
        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                String body = response.body().string();
                System.out.println("body: " + body);
            }
        }
    }

    @Test
    void test_post() throws Exception {
        Map<String, String> formParamMap = Map.of("name", "abc", "code", "123");

        final FormBody.Builder formBuilder = new FormBody.Builder();
        formParamMap.forEach(formBuilder::add);
        RequestBody requestBody = formBuilder.build();

        Request request = new Request.Builder()
                .url(requestUrl)
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
            }
            if (response.body() != null) {
                System.out.println("body: " + response.body().string());
            }
        }
    }
}
