package com.example.restapiexample.adapter.outbound.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.restapiexample.core.dbentity.Movie;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MovieJPARepositoryTest {
  @Autowired
  MovieJPARepository movieJPARepository;

  @Test
  void findByTitleAndGenreTest(){
    Movie movie1= Movie.builder().id(1L).title("abc").description("def").genre("action").rating(7.8).build();
    movieJPARepository.save(movie1);
    Movie movie2= Movie.builder().id(2L).title("ghi").description("def").genre("action").rating(6.8).build();
    movieJPARepository.save(movie2);
    List<Movie> movieList=movieJPARepository.findByTitleAndGenre("abc","action");
    assertThat(movieList.get(0).getId()).isEqualTo(1);
  }
}
