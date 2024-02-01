package com.orchastration.movie.service;

import ch.qos.logback.core.util.TimeUtil;
import com.orchastration.movie.client.MovieCrudClient;
import com.orchastration.movie.models.Film;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieCrudClient movieCrudClient;
    @Override
    public Mono<Film> getMovieById(int id) {
        long start = System.currentTimeMillis();
        Mono<Film> response = movieCrudClient.getMovie(id);
        long end = System.currentTimeMillis();
        log.info("Time taken : {}", end-start);
        return response.doOnNext(res -> mapResponse(res,start));
    }

    @Override
    public Flux<Film> getTopNMovies(int numOfTop) {
        Flux<Film> response= movieCrudClient.getTopNMovies(numOfTop);

        return response.doOnNext(res -> mapResponse(res, 0));
    }

    private void mapResponse(Film res, long start) {
        long end = System.currentTimeMillis();
        log.info("Time taken to reach map : {}", end-start);
        res.setTotalCast(res.getActors().size());
    }

    @Override
    public Flux<Film> getAllMovies() {
        return null;
    }

    @Override
    public Flux<Map<Integer, Film>> getAllMoviesAsMap() {
        return null;
    }

    @Override
    public Flux<Film> getMovieListByIds(Integer[] movieId) {
        return Flux.fromArray(movieId).flatMapSequential(this::getMovieById);
    }

}
