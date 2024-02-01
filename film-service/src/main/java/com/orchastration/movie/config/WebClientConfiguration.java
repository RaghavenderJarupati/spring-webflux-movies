package com.orchastration.movie.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
public class WebClientConfiguration {

 /* @Bean(name="oldWebClient")
  public WebClient configWebClient(WebClient.Builder builder) {

    ConnectionProvider connectionProvider =
        ConnectionProvider.builder("fixed")
            .maxConnections(500)
            .maxIdleTime(Duration.ofSeconds(20))
            .maxLifeTime(Duration.ofSeconds(60))
            .pendingAcquireTimeout(Duration.ofSeconds(60))
            .evictInBackground(Duration.ofSeconds(60))
            .build();

    HttpClient httpClient = HttpClient.create(connectionProvider);
    httpClient.warmup().block();

    var reactorClientHttpConnector = new ReactorClientHttpConnector(httpClient);

    var wc = builder *//*.baseUrl("/")*//*.clientConnector(reactorClientHttpConnector).build();

    var wca = WebClientAdapter.create(wc);
    return HttpServiceProxyFactory.builder()
        .exchangeAdapter(wca)
        .build()
        .createClient(WebClient.class);
  }*/

  @Bean(name = "newWebClient")
  public WebClient webClient() {

    HttpClient httpClient =
        HttpClient.create()
            .tcpConfiguration(
                client ->
                    client
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                        .doOnConnected(
                            conn ->
                                conn.addHandlerLast(new ReadTimeoutHandler(10))
                                    .addHandlerLast(new WriteTimeoutHandler(10))));

    ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient.wiretap(true));

    return WebClient.builder()
        .baseUrl("http://localhost:8080")
        .clientConnector(connector)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }
}
