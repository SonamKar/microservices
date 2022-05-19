package com.example.additionalmovieinfo.adapter.outbound.repository;

import com.example.additionalmovieinfo.core.dbentity.AdditionalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalInfoJPARepository extends JpaRepository<AdditionalInfo,Long> {

}
