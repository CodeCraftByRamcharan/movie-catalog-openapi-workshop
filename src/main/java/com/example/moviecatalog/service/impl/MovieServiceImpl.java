package com.example.moviecatalog.service.impl;

import com.example.moviecatalog.model.Movie;
import com.example.moviecatalog.repository.MovieRepository;
import com.example.moviecatalog.service.MovieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repo;

    public MovieServiceImpl(MovieRepository repo) {
        this.repo = repo;
    }

    @Override
    public Movie create(Movie movie) {
        return repo.save(movie);
    }

    @Override
    public List<Movie> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Movie> getById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Movie update(Long id, Movie movie) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(movie.getTitle());
            existing.setGenre(movie.getGenre());
            existing.setRating(movie.getRating());
            existing.setReleaseYear(movie.getReleaseYear());
            existing.setDescription(movie.getDescription());
            return repo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Movie> findByGenre(String genre) {
        return repo.findByGenreIgnoreCase(genre);
    }

    @Override
    public List<Movie> findByMinRating(Double rating) {
        return repo.findByRatingGreaterThanEqual(rating);
    }

    @Override
    public List<Movie> searchByTitle(String partial) {
        return repo.findByTitleContainingIgnoreCase(partial);
    }

    @Override
    public List<Movie> getAllSortedByRatingDesc() {
        return repo.findAllByOrderByRatingDesc();
    }
}
