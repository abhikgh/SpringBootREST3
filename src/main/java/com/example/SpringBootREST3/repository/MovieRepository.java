package com.example.SpringBootREST3.repository;

import com.example.SpringBootREST3.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>{

    @Query("select e from Movie e where e.director = :director")
    List<Movie> getMoviesOfDirector(@PathVariable("director") String director);

    List<Movie> findByDirector(String director);

    @Query("select m from Movie m where m.nationalAward = true")
    List<Movie> getNationalAwardsMovies();

    List<Movie> findByDirectorAndGenre(String director, String genre);

    List<Movie> findByDirectorAndGenreAndNationalAward(String director, String genre, Boolean nationalAward);

    List<Movie> findByHero(String heroName);
}