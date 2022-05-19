package com.example.restapiexample.core.port.inbound;

import com.example.restapiexample.core.dto.response.MovieDetails;
import com.example.restapiexample.core.dto.response.MovieDetailsWithAddOns;

public interface MovieDetailsPort {
  MovieDetails fetchMovieDetails(long id);
  MovieDetailsWithAddOns fetchMovieDetailsWithAddOns(long id,String idType);
}
