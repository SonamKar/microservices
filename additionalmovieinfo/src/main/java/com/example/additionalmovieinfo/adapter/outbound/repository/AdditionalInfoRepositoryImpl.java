package com.example.additionalmovieinfo.adapter.outbound.repository;

import com.example.additionalmovieinfo.core.dbentity.AdditionalInfo;
import com.example.additionalmovieinfo.core.port.outbound.AdditionalInfoRepository;
import com.example.additionalmovieinfo.exception.util.MyExceptionBuilder;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdditionalInfoRepositoryImpl implements AdditionalInfoRepository {
  private final AdditionalInfoJPARepository additionalInfoJPARepository;
  private final MyExceptionBuilder myExceptionBuilder;

  public AdditionalInfo fetchAdditionalInfoById(long id){
    Optional<AdditionalInfo> additionalInfoOptional = additionalInfoJPARepository.findById(id);
    if(additionalInfoOptional.isPresent()) {
      return additionalInfoOptional.get();
    }
    else {
      myExceptionBuilder.throwResourceNotFoundException
          ("BE-02", "NOT_FOUND", "ERR-BE-02", "No details with such ID exists");
//      throw new RuntimeException("No details with such ID exists");
    }
    return null;
  }
}
