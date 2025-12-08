package com.example.moviecatalog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    private String genre;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double rating;

    private Integer releaseYear;

    @Column(length = 2000)
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
