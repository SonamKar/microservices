package com.example.additionalmovieinfo.core.service;

import com.example.additionalmovieinfo.core.dbentity.AdditionalInfo;
import com.example.additionalmovieinfo.core.dto.response.MovieAdditionalInfo;
import com.example.additionalmovieinfo.core.port.inbound.MovieAdditionalInfoPort;
import com.example.additionalmovieinfo.core.port.outbound.AdditionalInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieAdditionalInfoService implements MovieAdditionalInfoPort {

  private final AdditionalInfoRepository additionalInfoRepositoryImpl;
  @Override
  public MovieAdditionalInfo fetchMovieAdditionalDetails(long id) {
    AdditionalInfo additionalInfo=additionalInfoRepositoryImpl.fetchAdditionalInfoById(id);
    return MovieAdditionalInfo.builder()
        .id(additionalInfo.getId())
        .cast(additionalInfo.getCast())
        .director(additionalInfo.getDirector())
        .imdbRating(additionalInfo.getImdbRating())
        .name(additionalInfo.getName())
        .build();
  }
}
