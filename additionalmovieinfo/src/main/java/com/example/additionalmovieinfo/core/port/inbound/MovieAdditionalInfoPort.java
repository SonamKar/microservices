package com.example.additionalmovieinfo.core.port.inbound;

import com.example.additionalmovieinfo.core.dto.response.MovieAdditionalInfo;

public interface MovieAdditionalInfoPort {
  MovieAdditionalInfo fetchMovieAdditionalDetails(long id);
}
