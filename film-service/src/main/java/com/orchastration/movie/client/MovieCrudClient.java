package com.orchastration.movie.client;

import com.orchastration.movie.models.Film;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieCrudClient {
    public Mono<Film> getMovie(int id);

    public Flux<Film> getTopNMovies(int top);
    
    
    
}
