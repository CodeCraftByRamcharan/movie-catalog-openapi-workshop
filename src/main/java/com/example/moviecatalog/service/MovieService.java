package com.example.moviecatalog.service;

import com.example.moviecatalog.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    Movie create(Movie movie);
    List<Movie> getAll();
    Optional<Movie> getById(Long id);
    Movie update(Long id, Movie movie);
    void delete(Long id);
    List<Movie> findByGenre(String genre);
    List<Movie> findByMinRating(Double rating);
    List<Movie> searchByTitle(String partial);
    List<Movie> getAllSortedByRatingDesc();
}
