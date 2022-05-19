package com.example.additionalmovieinfo.adapter.inbound.controller;

import com.example.additionalmovieinfo.annotation.StcRestController;
import com.example.additionalmovieinfo.core.dto.response.MovieAdditionalInfo;
import com.example.additionalmovieinfo.core.port.inbound.MovieAdditionalInfoPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
//@RestController
@StcRestController
public class AdditionalInfoController {
  private final MovieAdditionalInfoPort movieAdditionalInfoService;

  @GetMapping("/additionalInfos/{id}")
  public MovieAdditionalInfo getAllMovieAdditionalDetails(@PathVariable long id,@RequestHeader String idType
  ) {
    MovieAdditionalInfo movieAdditionalInfo = movieAdditionalInfoService.fetchMovieAdditionalDetails(id);
    log.info("The additional details for movies with id {} :: {}",id, movieAdditionalInfo);
    return movieAdditionalInfo;
  }
}
