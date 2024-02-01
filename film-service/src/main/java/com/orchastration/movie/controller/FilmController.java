package com.orchastration.movie.controller;

import com.orchastration.movie.models.Film;
import com.orchastration.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmController {

    private final MovieService movieService;

@GetMapping("/movie/{id}")
    public Mono<Film> getMovieById(@PathVariable("id") int id){
    return movieService.getMovieById(id);
}
    @GetMapping("/movie/list/{ids}")
    public Flux<Film> getMovieListByIds(@PathVariable("ids") Integer[] ids){
        return movieService.getMovieListByIds(ids);
    }

    @GetMapping("/movie/top-movies/{numOfTop}")
    public Flux<Film> getTopNMovies(@PathVariable("numOfTop") int numOfTop){
        return movieService.getTopNMovies(numOfTop);
    }

    @GetMapping("/movies")
    public Flux<Film> getAllMovies(){
    return movieService.getAllMovies();
    }

    @GetMapping("/movies/map")
    public Flux<Map<Integer, Film>> getAllMoviesAsMap(){
        return movieService.getAllMoviesAsMap();
    }
}
