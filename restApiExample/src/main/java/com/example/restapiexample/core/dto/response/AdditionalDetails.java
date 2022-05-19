package com.example.restapiexample.core.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdditionalDetails {

  @JsonProperty("movieId")
  private long id;

  @JsonProperty("movieName")
  private String name;

  @JsonProperty("directorName")
  private String director;

  @JsonProperty("imdbRating")
  private double imdbRating;

  @JsonProperty("cast")
  private String cast;
}
