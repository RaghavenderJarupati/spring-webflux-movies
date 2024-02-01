package com.orchastration.movie.service;

import com.orchastration.movie.models.Film;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface MovieService {
    Mono<Film> getMovieById(int id);

    Flux<Film> getTopNMovies(int numOfTop);

    Flux<Film> getAllMovies();

    Flux<Map<Integer, Film>> getAllMoviesAsMap();

    Flux<Film> getMovieListByIds(Integer[] id);
}
