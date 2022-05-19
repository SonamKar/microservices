package com.example.restapiexample.adapter.outbound.restclient;

import com.example.restapiexample.core.dto.restresponse.AdditionalInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    url = "${additional-info-ms.url}",
    name = "additional-info-ms"
    )
public interface AdditionalInfoFeignClient {
    @GetMapping("/additionalInfos/{id}")
    AdditionalInfo getAdditionalInfoById(@PathVariable long id
    ,@RequestHeader String idType
    );
}
