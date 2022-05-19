package com.example.restapiexample.adapter.outbound.restclient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.when;

import com.example.restapiexample.core.dto.restresponse.AdditionalInfo;
import com.example.restapiexample.core.port.outbound.AdditionalInfoRestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {
    AdditionalInfoRestClientImpl.class
})
class AdditionalInfoRestClientTestImpl {
  @Autowired
  AdditionalInfoRestClient additionalInfoRestClient;
  @MockBean
  AdditionalInfoFeignClient additionalInfoFeignClient;

  @Test
  void fetchAdditionalInfoByIdTest(){
    AdditionalInfo additionalInfo=AdditionalInfo.builder()
        .id(1L)
        .movieName("abcd")
        .directorName("director")
        .cast("aamir")
        .imdbRating(6.8)
        .build();
    when(additionalInfoFeignClient.getAdditionalInfoById(anyLong(),anyString())).thenReturn(additionalInfo);
    AdditionalInfo additionalInfoResponse=additionalInfoRestClient.fetchAdditionalInfoById(1L,"normal");
    assertThat(additionalInfoResponse.getCast()).isEqualTo("aamir");
    assertThat(additionalInfoResponse.getImdbRating()).isEqualTo(6.8);
  }
}
