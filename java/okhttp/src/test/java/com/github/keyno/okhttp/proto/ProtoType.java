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

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * POST json type.
     *
     * @throws Exception
     */
    @Test
    void test_post_json() throws Exception {
        final String requestUrl = "http://localhost:8080/api/webclient/test3";

        RequestBody requestBody = RequestBody.create(
            JSON,
            new ObjectMapper()
                .writeValueAsString(
                    new JsonDataTest(
                        "x",
                        List.of(new JsonDataTest.JsonDataChildren("a", "b"))
                )
            )
        );

        Request request = new Request.Builder()
                .url(requestUrl)
                .post(requestBody)
                //.removeHeader("User-Agent")
                .header("User-Agent", "nyannyan")
                .addHeader("sample", "header")
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

    public static class JsonDataTest {

        @JsonProperty("v")
        private final String value;
        @JsonProperty("c")
        private final List<JsonDataChildren> children;

        @JsonCreator
        public JsonDataTest(@JsonProperty("v") String value, @JsonProperty("c") List<JsonDataChildren> children) {
            this.value = value;
            this.children = children;
        }

        @Override
        public String toString() {
            return String.format("JsonDataTest[ value: %s, children: %s ]", value, Optional.ofNullable(children).map(Objects::toString).orElse("null"));
        }

        public static class JsonDataChildren {
            @JsonProperty("value1")
            private String value1;

            @JsonProperty("value2")
            private String value2;

            @JsonCreator
            public JsonDataChildren(@JsonProperty("value1") String value1, @JsonProperty("value2") String value2) {
                this.value1 = value1;
                this.value2 = value2;
            }

            @Override
            public String toString() {
                return String.format("JsonDataChildren[ value1: %s, value2: %s ]", value1, value2);
            }
        }
    }
}
