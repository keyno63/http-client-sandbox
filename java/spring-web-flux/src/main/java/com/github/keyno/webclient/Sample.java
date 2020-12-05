package com.github.keyno.webclient;

import com.github.keyno.webclient.client.WebClientSample;

public class Sample {
    public static void main(String[] args) {
        WebClientSample client = new WebClientSample();
        final String url = "http://localhost:8080/api/header/userAgent";

        // get
        String getValue = client.get(url);
        System.out.println(getValue);

        // post
        Object body = "{\"value\": \"hello world\"}";
        String postValue = client.post(url, body);

        System.out.println(postValue);
    }
}
