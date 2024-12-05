package com.example.SpringBootREST3.repository;

import com.example.SpringBootREST3.entity.MovieNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieNewRepository extends JpaRepository<MovieNew, Integer> {
}
