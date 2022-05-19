package com.example.restapiexample.core.dto.restresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdditionalInfo {
  @JsonProperty("movieId")
  private long id;

  private String movieName;

  private String directorName;

  private double imdbRating;

  private String cast;

}
