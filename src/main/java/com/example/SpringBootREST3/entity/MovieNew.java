package com.example.SpringBootREST3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MOVIE_NEW")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieNew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //GenerationType.IDENTITY :: automatically assign an id to the object when its row is inserted
    //                           movie.setMovieId(100) doesn't take 100, takes the next value in sequence
    @Column(name = "movie_Id")
    private Integer movieId;

    @Column(name = "movie_Name")
    private String movieName;

    @Column(name = "director")
    private String director;

    @Column(name = "genre")
    private String genre;

    @Column(name = "hero")
    private String hero;

    @Column(name = "year")
    private String year;

    @Column(name = "national_Award")
    private Boolean nationalAward;
}
