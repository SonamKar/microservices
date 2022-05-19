package com.example.restapiexample.adapter.outbound.repository;

import com.example.restapiexample.core.dbentity.Movie;
import com.example.restapiexample.core.port.outbound.MovieRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class MovieRepositoryImpl implements MovieRepository {

  @Autowired
  MovieJPARepository movieJPARepository;

  public List<Movie> fetchAllMovieDetails() {
    return movieJPARepository.findAll();
  }

  public Movie fetchMovieById(long id) {
    Optional<Movie> movieOpt = movieJPARepository.findById(id);
    if (movieOpt.isPresent()) {
      return movieOpt.get();
    }
    return null;
  }

  public long createMovieDetails(Movie movie) {
    return movieJPARepository.save(movie).getId();
  }

  public List<Movie> fetchMovieByTitleAndGenre(String title, String genre) {
    return movieJPARepository.findByTitleAndGenre(title, genre);
  }
}
