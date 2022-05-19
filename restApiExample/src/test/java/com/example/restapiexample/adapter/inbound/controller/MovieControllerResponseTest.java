package com.example.restapiexample.adapter.inbound.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.restapiexample.core.dbentity.Movie;
import com.example.restapiexample.core.dbentity.Viewer;
import com.example.restapiexample.core.dto.response.AdditionalDetails;
import com.example.restapiexample.core.dto.response.MovieDetailsWithAddOns;
import com.example.restapiexample.core.port.inbound.MovieDetailsPort;
import com.example.restapiexample.core.port.outbound.MovieRepository;
import com.example.restapiexample.core.port.outbound.ViewerRepository;
import com.example.restapiexample.exception.util.MyExceptionBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(
    controllers = {
        MovieController.class,
        MyExceptionBuilder.class
    }
)
@Slf4j
class MovieControllerResponseTest {
  @Autowired
  MockMvc mockMvc;
  @MockBean
  MovieRepository movieRepository;
  @MockBean
  ViewerRepository viewerRepository;
  @MockBean
  MovieDetailsPort movieDetails;
  @Autowired
  ObjectMapper objectMapper;

  @Test
  void getAllMovieDetailsTest_Success() throws Exception{
    List<Movie> movieList= Arrays.asList(Movie.builder()
        .id(1)
        .description("abc")
        .title("Antim")
        .genre("action")
        .rating(8.5)
        .build(),
        Movie.builder()
            .id(2)
            .description("def")
            .title("Venom")
            .genre("friction")
            .rating(8.7)
            .build());
    log.info("movie list :: {}",objectMapper.writeValueAsString(movieList));
    when(movieRepository.fetchAllMovieDetails()).thenReturn(movieList);

    this.mockMvc.perform(
        get("/movies"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$..id").value(Lists.newArrayList(1,2)))
//        .andExpect(jsonPath("$.[0].id").value(1))
        .andExpect(jsonPath("$.[1].title").value("Venom"));
  }

  @Test
  void getViewersByName_Success() throws Exception{
    List<Viewer> viewerList= Arrays.asList(Viewer.builder()
        .name("Santosh")
        .age(23)
        .preferredMovieType("Action")
        .build());
    when(viewerRepository.getViewerDetails(anyString())).thenReturn(viewerList);
    this.mockMvc.perform(
        get("/viewers")
            .param("name","Raj"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$..preferredMovieType").value("Action"));
  }

  @Test
  void getAdditionalMovieDetails_Success() throws Exception{
    MovieDetailsWithAddOns movieDetailsWithAddOns=MovieDetailsWithAddOns.builder()
        .id(1L)
        .additionalInfo(AdditionalDetails.builder()
            .name("abc")
            .imdbRating(8.7)
            .director("def")
            .cast("salman")
            .id(1L)
            .build())
        .movieTicketPrice(200)
        .description("describe")
        .genre("action")
        .rating(3.4)
        .build();
    log.info("movie list :: {}",objectMapper.writeValueAsString(movieDetailsWithAddOns));
    when(movieDetails.fetchMovieDetailsWithAddOns(anyLong(),anyString())).thenReturn(movieDetailsWithAddOns);
    this.mockMvc.perform(
        get("/additionalDetails/{id}","124")
            .header("idType","special"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$..additionalInfo.cast").value("salman"))
        .andExpect(jsonPath("$..additionalInfo.movieId").value(1))
        .andExpect(jsonPath("$.movieId").value(1))
        .andExpect(jsonPath("$.genre").value("action"));
  }
}