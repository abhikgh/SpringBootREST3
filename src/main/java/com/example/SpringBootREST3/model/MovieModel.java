package com.example.SpringBootREST3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieModel {

    @NotBlank
    @Size(min = 5, message = "Movie name must be 5 chars")
    private String movieName;
    private String director;
    private String genre;
    private String hero;
    private String year;
    private Boolean nationalAward;
}
