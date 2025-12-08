package com.example.moviecatalog.controller;

import com.example.moviecatalog.exception.ResourceNotFoundException;
import com.example.moviecatalog.model.Movie;
import com.example.moviecatalog.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie movie) {
        Movie saved = service.create(movie);
        return ResponseEntity.created(URI.create("/api/movies/" + saved.getId())).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAll(
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "minRating", required = false) Double minRating,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sortByRating", required = false) Boolean sortByRating
    ) {
        if (genre != null) return ResponseEntity.ok(service.findByGenre(genre));
        if (minRating != null) return ResponseEntity.ok(service.findByMinRating(minRating));
        if (search != null) return ResponseEntity.ok(service.searchByTitle(search));
        if (Boolean.TRUE.equals(sortByRating)) return ResponseEntity.ok(service.getAllSortedByRatingDesc());
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable Long id, @Valid @RequestBody Movie movie) {
        try {
            Movie updated = service.update(id, movie);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            throw new ResourceNotFoundException("Movie not found with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
