package com.example.restapiexample.core.port.outbound;

import com.example.restapiexample.core.dbentity.Movie;
import java.util.List;


public interface MovieRepository {
    List<Movie> fetchAllMovieDetails();
    Movie fetchMovieById(long id);
    long createMovieDetails(Movie movie);
    List<Movie> fetchMovieByTitleAndGenre(String title,String genre);
}
