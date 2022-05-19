package com.example.restapiexample.config;

import com.example.restapiexample.adapter.outbound.repository.MovieJPARepository;
import com.example.restapiexample.adapter.outbound.repository.MovieRepositoryImpl;
import com.example.restapiexample.core.dbentity.Movie;
import com.example.restapiexample.core.port.outbound.MovieRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@TestConfiguration
public class MovieDetailsBeanTest {
//  @MockBean
//  MovieJPARepository movieJPARepository;

  @Bean
  public MovieRepository getMovieRepository(){
    return new MovieRepositoryImpl();
  }
  @Bean
  public MovieJPARepository getMovieJPARepository(){
    return new MovieJPARepository() {
      @Override
      public List<Movie> findByTitleAndGenre(String title, String genre) {
        return null;
      }

      @Override
      public List<Movie> findAll() {
        return null;
      }

      @Override
      public List<Movie> findAll(Sort sort) {
        return null;
      }

      @Override
      public List<Movie> findAllById(Iterable<Long> iterable) {
        return null;
      }

      @Override
      public <S extends Movie> List<S> saveAll(Iterable<S> iterable) {
        return null;
      }

      @Override
      public void flush() {

      }

      @Override
      public <S extends Movie> S saveAndFlush(S s) {
        return null;
      }

      @Override
      public void deleteInBatch(Iterable<Movie> iterable) {

      }

      @Override
      public void deleteAllInBatch() {

      }

      @Override
      public Movie getOne(Long aLong) {
        return null;
      }

      @Override
      public <S extends Movie> List<S> findAll(Example<S> example) {
        return null;
      }

      @Override
      public <S extends Movie> List<S> findAll(Example<S> example, Sort sort) {
        return null;
      }

      @Override
      public Page<Movie> findAll(Pageable pageable) {
        return null;
      }

      @Override
      public <S extends Movie> S save(S s) {
        return null;
      }

      @Override
      public Optional<Movie> findById(Long aLong) {
        return Optional.empty();
      }

      @Override
      public boolean existsById(Long aLong) {
        return false;
      }

      @Override
      public long count() {
        return 0;
      }

      @Override
      public void deleteById(Long aLong) {

      }

      @Override
      public void delete(Movie movie) {

      }

      @Override
      public void deleteAll(Iterable<? extends Movie> iterable) {

      }

      @Override
      public void deleteAll() {

      }

      @Override
      public <S extends Movie> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
      }

      @Override
      public <S extends Movie> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
      }

      @Override
      public <S extends Movie> long count(Example<S> example) {
        return 0;
      }

      @Override
      public <S extends Movie> boolean exists(Example<S> example) {
        return false;
      }
    };
  }
}
