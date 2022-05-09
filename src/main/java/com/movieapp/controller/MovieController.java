package com.movieapp.controller;

import com.movieapp.exception.MovieNotFoundException;
import com.movieapp.model.Movie;
import com.movieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MovieController {
     final MovieService movieService;

    @GetMapping("/movies")
    public List<Movie> getAllMovieDetails(){
        log.info("Fetched Movie details successfully....");
        return movieService.fetchMovies();
    }

    @GetMapping("/movies/{movie_id}")
    public ResponseEntity<Movie> getAllMovieById(@PathVariable long movie_id){
        //log.info("Fetched Movie detail successfully with id {} ",movie_id);

        Movie movie=movieService.fetchMovieById(movie_id);
        if(movie==null){
            throw new MovieNotFoundException("Movie is not present");
        }
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @PostMapping("/movies")
    public Movie createMovieDetail(@RequestBody Movie movie){
        log.info("Created movie successfully");
         return movieService.createMovie(movie);
    }
    @DeleteMapping("/movies/{id}")
    public void deleteMovieDetail(@PathVariable long id){
        log.info(String.format("Movie with id: %s is successfully deleted",id));
        movieService.deleteMovie(id);
    }
    @PutMapping("/movies/{id}")
    public void updateMovieDetail(@RequestBody Movie movie,@PathVariable long id){
         movieService.updateMovie(movie,id);
    }
    @GetMapping("/moviesByTitleAndGenre")
    public List<Movie> getAllMovieByTitleAndGenre(@RequestParam String title,@RequestParam String genre){
        log.info("Movie with title {} and genre {} fetched successfully",title,genre);
        return movieService.getMoviesByTitleAndGenre(title,genre);
    }
}
