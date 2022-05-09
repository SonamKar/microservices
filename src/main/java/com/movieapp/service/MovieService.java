package com.movieapp.service;

import com.movieapp.model.Movie;
import com.movieapp.repository.MovieRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class MovieService {
    private final MovieRepository movieRepository;
    @CircuitBreaker(name="MovieMS")
    @Retry(name="MovieMS")
    @Bulkhead(name="MovieMS",type = Bulkhead.Type.SEMAPHORE)
    public List<Movie> fetchMovies(){

        return movieRepository.findAll();
    }
    public Movie fetchMovieById(long id){

        Optional<Movie> movieObject=movieRepository.findById(id);

       /* if(movieObject.isPresent()){
            return movieObject.get();
        }
        else
            return null;
         */
        return movieObject.orElse(null);
    }
    public Movie createMovie(Movie movie){
        movieRepository.save(movie);
        return movie;
    }

    public void deleteMovie(long id){

        movieRepository.deleteById(id);
    }
    public Movie updateMovie(Movie movie,long id){
       Optional<Movie> optionalMovieObject=movieRepository.findById(id);
        if(optionalMovieObject.isPresent()){
            Movie newMovieObj=optionalMovieObject.get();
            newMovieObj.setId(id);
            newMovieObj.setTitle(movie.getTitle());
            newMovieObj.setDescription(movie.getDescription());
            newMovieObj.setRating(movie.getRating());
            newMovieObj.setGenre(movie.getGenre());

            movieRepository.save(newMovieObj);
            return newMovieObj;
        }
        else{
           movieRepository.save(movie);
           return movie;
           }
           /*
        movie.setId(id);
        movieRepository.save(movie);
        return movie;
        */



    }
    public List<Movie> getMoviesByTitleAndGenre(String title,String genre){
        return movieRepository.findByTitleAndGenre(title,genre);
    }
}
