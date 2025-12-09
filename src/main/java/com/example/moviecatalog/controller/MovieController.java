package com.example.moviecatalog.controller;

import com.example.moviecatalog.exception.ResourceNotFoundException;
import com.example.moviecatalog.model.Movie;
import com.example.moviecatalog.service.MovieService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Movie API", description = "Operations for managing movies in the catalog")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Create a new movie",
            description = "Add a new movie to the catalog",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Movie successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Movie.class),
                            examples = @ExampleObject(value = "{ \"title\": \"Inception\", \"director\": \"Christopher Nolan\", \"year\": 2010 }"),
                            encoding = @Encoding(name = "title")
                    )
            )
    })
    public ResponseEntity<Movie> createMovie(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Movie object to create",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Movie.class)
                    )
            )
            @Valid @RequestBody Movie movie
    ) {
        Movie saved = service.create(movie);
        return ResponseEntity.created(URI.create("/api/movies/" + saved.getId())).body(saved);
    }

    @GetMapping
    @Operation(summary = "Get all movies", description = "Retrieve all movies with optional filters and sorting")
    @Parameters({
            @Parameter(name = "genre", description = "Filter by genre", in = ParameterIn.QUERY),
            @Parameter(name = "minRating", description = "Filter by minimum rating", in = ParameterIn.QUERY),
            @Parameter(name = "search", description = "Search by title keyword", in = ParameterIn.QUERY),
            @Parameter(name = "sortByRating", description = "Sort results by rating descending", in = ParameterIn.QUERY)
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of movies",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Movie.class))
                    )
            )
    })
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
    @Operation(summary = "Get movie by ID", description = "Retrieve a movie by its unique ID")
    @Parameters({
            @Parameter(name = "id", description = "Movie ID", required = true, in = ParameterIn.PATH)
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Movie found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Movie.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<Movie> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing movie", description = "Update the details of an existing movie")
    @Parameters({
            @Parameter(name = "id", description = "Movie ID to update", required = true, in = ParameterIn.PATH)
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Movie successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Movie.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<Movie> update(@PathVariable Long id, @Valid @RequestBody Movie movie) {
        try {
            Movie updated = service.update(id, movie);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            throw new ResourceNotFoundException("Movie not found with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a movie", description = "Remove a movie by its ID")
    @Parameters({
            @Parameter(name = "id", description = "Movie ID to delete", required = true, in = ParameterIn.PATH)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Movie successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Hidden
    @GetMapping("/hidden")
    public String hiddenEndpoint() {
        return "This endpoint is hidden in Swagger UI";
    }
}
