package com.example.restapiexample.core.port.outbound;

import com.example.restapiexample.core.dto.restresponse.AdditionalInfo;

public interface AdditionalInfoRestClient {
   AdditionalInfo fetchAdditionalInfoById(long id,String idType);
}
