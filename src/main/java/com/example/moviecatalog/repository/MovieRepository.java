package com.example.moviecatalog.repository;

import com.example.moviecatalog.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByGenreIgnoreCase(String genre);
    List<Movie> findByRatingGreaterThanEqual(Double rating);
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findAllByOrderByRatingDesc();
}
