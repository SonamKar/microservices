package com.example.restapiexample.adapter.inbound.controller;


import com.example.restapiexample.core.dbentity.Movie;
import com.example.restapiexample.core.dbentity.Viewer;
import com.example.restapiexample.core.dto.response.MovieDetails;
import com.example.restapiexample.core.dto.response.MovieDetailsWithAddOns;
import com.example.restapiexample.core.port.inbound.MovieDetailsPort;
import com.example.restapiexample.core.port.outbound.MovieRepository;
import com.example.restapiexample.core.port.outbound.ViewerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class MovieController {

  private final MovieRepository movieRepositoryImpl;
  private final ViewerRepository viewerRepositoryImpl;
  private final MovieDetailsPort movieDetailsService;

  @GetMapping("/movies")
  public List<Movie> getAllMovieDetails() {
    List<Movie> movieList = movieRepositoryImpl.fetchAllMovieDetails();
    log.info("The movie details :: {}", movieList);
    return movieList;
  }

  @GetMapping("/movies/{id}")
  public Movie getAllMovieById(@PathVariable("id") long id) {
    return movieRepositoryImpl.fetchMovieById(id);
  }

  @PostMapping("/movie")
  public long createMovieDetail(@RequestBody Movie movie) {
    return movieRepositoryImpl.createMovieDetails(movie);
  }

  @GetMapping("/viewers")
  public List<Viewer> getViewersByName(@RequestParam String name) {
    return viewerRepositoryImpl.getViewerDetails(name);
  }

  @GetMapping("/movie/{title}/{genre}")
  public List<Movie> getAllMovieByIdAndTitle(@PathVariable String title,@PathVariable("genre")String genre){
    return movieRepositoryImpl.fetchMovieByTitleAndGenre(title,genre);
  }

  @GetMapping("/movieDetails/{id}")
  public MovieDetails getMovieDetails(@PathVariable long id){
    return movieDetailsService.fetchMovieDetails(id);
  }

  @GetMapping("/additionalDetails/{id}")
  public MovieDetailsWithAddOns getAdditionalMovieDetails(@PathVariable long id,@RequestHeader String idType){
    return movieDetailsService.fetchMovieDetailsWithAddOns(id,idType);
  }

//  @DeleteMapping("/movie/{id}")
//  public void deleteMovieDetail(@PathVariable("id")long id){
//    movieRepository.deleteById(id);
//  }
//  @PutMapping("/movie/{id}")
//  public void updateMovieDetail(@PathVariable("id")long id,@RequestBody Movie movie){
//    movieRepository.save(movie);
//    movie.setId(id);
//  }

}
