package com.example.restapiexample.core.dbentity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Viewer {

  private String name;

  private int age;

  private String preferredMovieType;
}
