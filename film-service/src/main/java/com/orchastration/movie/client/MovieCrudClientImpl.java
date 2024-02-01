package com.orchastration.movie.client;

import com.orchastration.movie.models.Film;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovieCrudClientImpl implements MovieCrudClient {

  private final WebClient newWebClient;

  public Mono<Film> getMovie(int id) {
    String url = "http://localhost:8080/film/{id}";
    url = url.replace("{id}", String.valueOf(id));
    log.info("Url: {}", url);
    URI uri = URI.create(url);

    /* return newWebClient.get()
              .uri(uri)
              .retrieve()
              .bodyToMono(Film.class);*/
    return newWebClient
        .get()
        .uri(uri)
        .accept(MediaType.APPLICATION_JSON)
        .headers(
        httpHeaders -> {
          httpHeaders.add("client_id", "webflux-spring-orc-svc");
        })
        .retrieve()
        .onStatus(
            httpStatus -> !httpStatus.is2xxSuccessful(),
            clientResponse -> handleErrorResponse(clientResponse.statusCode(), id))
        .bodyToMono(Film.class);
  }

  public Flux<Film> getTopNMovies(int topNRecords) {
    String url = "http://localhost:8080/film/first/{topNRecords}";
    url = url.replace("{topNRecords}", String.valueOf(topNRecords));
    log.info("Url: {}", url);
    URI uri = URI.create(url);
    return newWebClient
        .get()
        .uri(uri)
        .headers(
            httpHeaders -> {
              httpHeaders.add("client_id", "webflux-spring-orc-svc");
            })
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .onStatus(
            httpStatusCode -> !httpStatusCode.is2xxSuccessful(),
            clientResponse -> handleErrorResponse(clientResponse.statusCode(), topNRecords))
        .bodyToFlux(Film.class)
        .onErrorResume(Exception.class, e -> Flux.empty());
  }

  private Mono<? extends Throwable> handleErrorResponse(HttpStatusCode httpStatusCode, int id) {
    return Mono.error(
        new Exception("Failed to fetch movie with id: " + id + ". Status code: " + httpStatusCode));
  }
}
