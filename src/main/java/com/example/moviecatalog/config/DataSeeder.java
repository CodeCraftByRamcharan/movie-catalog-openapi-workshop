package com.example.moviecatalog.config;

import com.example.moviecatalog.model.Movie;
import com.example.moviecatalog.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(MovieRepository repo) {
        return args -> {
            repo.save(new Movie("The Shawshank Redemption", "Drama", 9.3, 1994,
                    "Two imprisoned men bond over a number of years..."));
            repo.save(new Movie("The Godfather", "Crime", 9.2, 1972,
                    "The aging patriarch of an organized crime dynasty transfers control..."));
            repo.save(new Movie("The Dark Knight", "Action", 9.0, 2008,
                    "When the menace known as the Joker wreaks havoc and chaos..."));
            repo.save(new Movie("Inception", "Sci-Fi", 8.8, 2010,
                    "A thief who steals corporate secrets through use of dream-sharing technology..."));
            repo.save(new Movie("Interstellar", "Sci-Fi", 8.6, 2014,
                    "A team of explorers travel through a wormhole in space..."));
        };
    }
}
