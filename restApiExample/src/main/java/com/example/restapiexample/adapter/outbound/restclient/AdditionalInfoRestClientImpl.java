package com.example.restapiexample.adapter.outbound.restclient;

import com.example.restapiexample.core.dto.restresponse.AdditionalInfo;
import com.example.restapiexample.core.port.outbound.AdditionalInfoRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdditionalInfoRestClientImpl implements AdditionalInfoRestClient {
  private final AdditionalInfoFeignClient additionalInfoFeignClient;

  public AdditionalInfo fetchAdditionalInfoById(long id,String idType){
    return additionalInfoFeignClient.getAdditionalInfoById(id,idType);
  }
}
