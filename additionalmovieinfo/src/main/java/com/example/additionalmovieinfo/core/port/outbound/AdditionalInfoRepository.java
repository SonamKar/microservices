package com.example.additionalmovieinfo.core.port.outbound;

import com.example.additionalmovieinfo.core.dbentity.AdditionalInfo;


public interface AdditionalInfoRepository {
  AdditionalInfo fetchAdditionalInfoById(long id);
}
