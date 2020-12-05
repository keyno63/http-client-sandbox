package com.github.keyno.webclient.client;

import io.netty.channel.ChannelOption;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.HttpHeaders.USER_AGENT;

public class WebClientSample {
    private final WebClient webClient;

    public WebClientSample() {
        ClientHttpConnector connector = new ReactorClientHttpConnector(
                HttpClient.create()
                        .wiretap(true)
                        .tcpConfiguration(tcpClient -> tcpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                                1000))
        );
        webClient = WebClient.builder()
                .clientConnector(connector)
                .defaultCookie("SAMPLE", "sample_value")
                .defaultHeader(USER_AGENT, "") // UA 塗りつぶした場合
                .build();
    }

    public String get(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String post(String url, Object body) {
        return webClient.post()
                .uri(url)
                .contentType(APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
