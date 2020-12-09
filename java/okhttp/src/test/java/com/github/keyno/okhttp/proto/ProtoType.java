package com.github.keyno.okhttp.proto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ProtoType {

    private final static String requestUrl = "http://localhost:8080/hello";

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

    /**
     * POST form type.
     *
     * @throws Exception
     */
    @Test
    void test_post_form() throws Exception {
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
