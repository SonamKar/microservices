package com.example.restapiexample.core.service;

import com.example.restapiexample.core.dbentity.Movie;
import com.example.restapiexample.core.dto.response.AdditionalDetails;
import com.example.restapiexample.core.dto.response.MovieDetails;
import com.example.restapiexample.core.dto.response.MovieDetailsWithAddOns;
import com.example.restapiexample.core.dto.restresponse.AdditionalInfo;
import com.example.restapiexample.core.port.inbound.MovieDetailsPort;
import com.example.restapiexample.core.port.outbound.AdditionalInfoRestClient;
import com.example.restapiexample.core.port.outbound.MovieRepository;
import com.example.restapiexample.exception.util.MyExceptionBuilder;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieDetailsService implements MovieDetailsPort {
  private final MovieRepository movieRepositoryImpl;
  private final AdditionalInfoRestClient additionalInfoRestClientImpl;
  private final MyExceptionBuilder myExceptionBuilder;

  public MovieDetails fetchMovieDetails(long id){
    Movie movie=movieRepositoryImpl.fetchMovieById(id);
    if(!Objects.isNull(movie)){
      return MovieDetails.builder()
          .id(id)
          .description(movie.getDescription())
          .title(movie.getTitle())
          .genre(movie.getGenre())
          .rating(movie.getRating())
          .movieTicketPrice(calculatePrice(movie.getRating(),movie.getGenre()))
          .build();
    }else {
      myExceptionBuilder.throwResourceNotFoundException("BE-02", "NOT_FOUND", "ERR-BE-02", "No such movie exists");
    }
    return null;
  }
  public double calculatePrice(double rating,String genre){
    double price;
    if(rating>=5 && genre.equalsIgnoreCase("action")){
      price=100*rating;
    }else if(rating>=5 && genre.equalsIgnoreCase("horror")){
      price=200;
    }else{
      price=100;
    }
    return price;
  }

  @Override
  public MovieDetailsWithAddOns fetchMovieDetailsWithAddOns(long id,String idType) {
    Movie movie=movieRepositoryImpl.fetchMovieById(id);
    AdditionalInfo additionalInfo=additionalInfoRestClientImpl.fetchAdditionalInfoById(id,idType);
    if(!(Objects.isNull(movie)) && !(Objects.isNull(additionalInfo))){
      return MovieDetailsWithAddOns.builder()
          .id(id)
          .description(movie.getDescription())
          .title(movie.getTitle())
          .genre(movie.getGenre())
          .rating(movie.getRating())
          .movieTicketPrice(calculatePrice(movie.getRating(),movie.getGenre()))
          .additionalInfo(
              AdditionalDetails.builder()
                  .id(additionalInfo.getId())
                  .cast(additionalInfo.getCast())
                  .director(additionalInfo.getDirectorName())
                  .imdbRating(additionalInfo.getImdbRating())
                  .name(additionalInfo.getMovieName())
                  .build()
          )
          .build();
    }else{
      myExceptionBuilder.throwResourceNotFoundException(
          "BE-02",
          "NOT_FOUND",
          "ERR-BE-02",
          "No such movie/additional info exists");
    }
    return null;
  }
}
