package com.example.moviecatalog.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "movies")
@Schema(
        description = "Represents a movie in the catalog",
        example = "{ \"id\": 1, \"title\": \"Inception\", \"genre\": \"Sci-Fi\", \"rating\": 8.8, \"releaseYear\": 2010, \"description\": \"A mind-bending thriller by Christopher Nolan.\" }"
)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the movie", example = "1")
    private Long id;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Title of the movie", example = "Inception", required = true)
    private String title;

    @Schema(description = "Genre of the movie", example = "Sci-Fi")
    private String genre;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    @Schema(description = "Rating of the movie on a scale of 0.0 to 10.0", example = "8.8")
    private Double rating;

    @Schema(description = "Year the movie was released", example = "2010")
    private Integer releaseYear;

    @Column(length = 2000)
    @Schema(description = "Brief description or synopsis of the movie", example = "A mind-bending thriller by Christopher Nolan.")
    private String description;

    // Constructors, getters, setters
    public Movie() {}

    public Movie(String title, String genre, Double rating, Integer releaseYear, String description) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.description = description;
    }

    // getters and setters omitted for brevity — include all standard getters/setters
    // Alternatively enable Lombok @Data if you like
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
