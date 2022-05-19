package com.example.restapiexample.core.service;

import com.example.restapiexample.core.dbentity.Movie;
import com.example.restapiexample.core.dto.response.MovieDetails;
import com.example.restapiexample.core.dto.response.MovieDetailsWithAddOns;
import com.example.restapiexample.core.dto.restresponse.AdditionalInfo;
import com.example.restapiexample.core.port.outbound.AdditionalInfoRestClient;
import com.example.restapiexample.core.port.outbound.MovieRepository;
import com.example.restapiexample.exception.ResourceNotFoundException;
import com.example.restapiexample.exception.util.MyExceptionBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes =
    MovieDetailsService.class
)
@ContextConfiguration(classes = {MyExceptionBuilder.class})
@Slf4j
class MovieDetailsServiceTest {
  @Autowired
  MovieDetailsService movieDetailsService;
  @MockBean
  AdditionalInfoRestClient additionalInfoRestClient;
  @MockBean
  MovieRepository movieRepository;

  @Test
  void fetchMovieDetailsTest(){
    Movie movie=Movie.builder().id(1L).title("abc").description("def").genre("action").rating(7.8).build();
    when(movieRepository.fetchMovieById(anyLong())).thenReturn(movie);
    MovieDetails movieDetails=movieDetailsService.fetchMovieDetails(1);
    assertThat(movieDetails.getDescription()).isEqualTo("def");
    assertThat(movieDetails.getMovieTicketPrice()).isEqualTo(780);
  }

  @Test
  void fetchMovieDetailsWithAddOnsTest(){
    AdditionalInfo additionalInfo=AdditionalInfo.builder()
        .id(1L)
        .movieName("abcd")
        .directorName("director")
        .cast("aamir")
        .imdbRating(6.8)
        .build();
    when(additionalInfoRestClient.fetchAdditionalInfoById(anyLong(),anyString())).thenReturn(additionalInfo);

    Movie movie=Movie.builder().id(1L).title("abc").description("def").genre("action").rating(7.8).build();
    when(movieRepository.fetchMovieById(anyLong())).thenReturn(movie);

    MovieDetailsWithAddOns movieDetailsWithAddOns=movieDetailsService.fetchMovieDetailsWithAddOns(1,"normal");

    assertThat(movieDetailsWithAddOns.getDescription()).isEqualTo("def");
    assertThat(movieDetailsWithAddOns.getMovieTicketPrice()).isEqualTo(780);
    assertThat(movieDetailsWithAddOns.getAdditionalInfo().getImdbRating()).isEqualTo(6.8);
    assertThat(movieDetailsWithAddOns.getAdditionalInfo().getCast()).isEqualTo("aamir");
  }

  @Test
  void fetchMovieDetailsTest_ResourceNotFoundException(){
    when(movieRepository.fetchMovieById(anyLong())).thenReturn(null);
    ResourceNotFoundException exception=assertThrows(ResourceNotFoundException.class,
        ()->movieDetailsService.fetchMovieDetails(2));
    assertEquals("BE-02",exception.getCode());
    assertThat(exception.getReason()).isEqualTo("No such movie exists");
  }
  @Test
  void fetchMovieDetailsWithAddOnsTest_ResourceNotFoundException(){
    when(movieRepository.fetchMovieById(anyLong())).thenReturn(null);
    AdditionalInfo additionalInfo=AdditionalInfo.builder()
        .id(1L)
        .movieName("abcd")
        .directorName("director")
        .cast("aamir")
        .imdbRating(6.8)
        .build();
    when(additionalInfoRestClient.fetchAdditionalInfoById(anyLong(),anyString())).thenReturn(additionalInfo);

    ResourceNotFoundException exception=assertThrows(ResourceNotFoundException.class,
        ()->movieDetailsService.fetchMovieDetailsWithAddOns(2,"normal"));
    assertEquals("BE-02",exception.getCode());
    assertThat(exception.getReason()).isEqualTo("No such movie/additional info exists");
  }
}