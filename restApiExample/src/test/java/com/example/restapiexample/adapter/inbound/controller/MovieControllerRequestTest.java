package com.example.restapiexample.adapter.inbound.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.restapiexample.config.MovieDetailsBeanTest;
import com.example.restapiexample.core.port.inbound.MovieDetailsPort;
import com.example.restapiexample.core.port.outbound.MovieRepository;
import com.example.restapiexample.core.port.outbound.ViewerRepository;
import com.example.restapiexample.exception.util.MyExceptionBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
  controllers = {
      MovieController.class,
      MyExceptionBuilder.class
  })
@Import(MovieDetailsBeanTest.class)
class MovieControllerRequestTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
      // or @MockBean
  MovieRepository movieRepository;
  @MockBean
  ViewerRepository viewerRepository;
  @MockBean
  MovieDetailsPort movieDetails;

  @Test
  void getAllMovieDetailsTest_Success() throws Exception{
  this.mockMvc.perform(
      get("/movies"))
      .andExpect(status().isOk());
  }

  @Test
  void getViewersByName_Success() throws Exception{
    this.mockMvc.perform(
        get("/viewers")
            .param("name","Raj"))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  void getAdditionalMovieDetails_Success() throws Exception{
    this.mockMvc.perform(
        get("/additionalDetails/{id}",1)
          .header("idType","normal"))
        .andExpect(status().isOk());
  }
  @Test
  void getAdditionalMovieDetails_Failure() throws Exception{
    this.mockMvc.perform(
        get("/additionalDetails/{id}",12)
            .header("idType","normal"))
        .andExpect(status().isForbidden());
  }

}
