package com.example.restapiexample.adapter.outbound.repository;

import com.example.restapiexample.core.dbentity.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface MovieJPARepository extends JpaRepository<Movie,Long> {
     List<Movie> findByTitleAndGenre(String title, String genre) ;

}