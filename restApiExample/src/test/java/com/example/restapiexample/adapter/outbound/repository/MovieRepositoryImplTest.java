package com.example.restapiexample.adapter.outbound.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.restapiexample.core.dbentity.Movie;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.ribbon.FeignRibbonClientAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

@DataJpaTest(
    includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Component.class)
)
@ImportAutoConfiguration({
    RibbonAutoConfiguration.class,
    FeignRibbonClientAutoConfiguration.class,
    FeignAutoConfiguration.class
})
public class MovieRepositoryImplTest {
  @Autowired
  MovieRepositoryImpl movieRepository;

  @Test
  void fetchAllMovieDetailsTest() {
    final List<Movie> movieList =
        movieRepository.fetchAllMovieDetails();
    assertThat(movieList).isNotNull();
    assertThat(movieList.get(0).getTitle()).isEqualTo("abc");
    assertThat(movieList.get(1).getDescription()).isEqualTo("action movie");
  }
  @Test
  void fetchAllMovieDetailsByIdTest() {
    Movie movie = movieRepository.fetchMovieById(2);
    assertThat(movie).isNotNull();
    assertThat(movie.getTitle()).isEqualTo("movie2");
  }

  @Test
  void createMovieDetailsTest(){
    Movie movie=Movie.builder().id(1L).title("abc").description("def").genre("action").rating(7.8).build();
    long movieId = movieRepository.createMovieDetails(movie);
    assertThat(movieId).isEqualTo(1L);
  }
}
