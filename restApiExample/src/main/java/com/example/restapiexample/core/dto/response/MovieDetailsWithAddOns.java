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
public class MovieDetailsWithAddOns {
  @JsonProperty("movieId")
  private long id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("description")
  private String description;

  @JsonProperty("rating")
  private double rating;

  @JsonProperty("genre")
  private String genre;

  @JsonProperty("movieTicketPrice")
  private double movieTicketPrice;

  @JsonProperty("additionalInfo")
  private AdditionalDetails additionalInfo;
}
