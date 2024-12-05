package com.example.SpringBootREST3.service;

import com.example.SpringBootREST3.entity.Movie;
import com.example.SpringBootREST3.entity.MovieNew;
import com.example.SpringBootREST3.repository.MovieNewRepository;
import com.example.SpringBootREST3.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieNewRepository movieNewRepository;

    public List<Movie> getMoviesOfDirector(String director) {
        log.info("Inside getMoviesOfDirector service... ");
        List<Movie> movies = movieRepository.findByDirector(director);
        return movies;
    }

    public List<Movie> findByDirectorAndGenre(String director, String genre) {
        log.info("Inside getMoviesOfDirector service... ");
        List<Movie> movies = movieRepository.findByDirectorAndGenre(director, genre);
        return movies;
    }

    public Number countUsers() {
        return movieRepository.findAll().stream().count();
    }

    public MovieNew addMovie(MovieNew movieNew) {
        return movieNewRepository.saveAndFlush(movieNew);
    }
}
