package com.example.additionalmovieinfo.adapetr.inbound.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.additionalmovieinfo.adapter.inbound.controller.AdditionalInfoController;
import com.example.additionalmovieinfo.core.port.inbound.MovieAdditionalInfoPort;
import com.example.additionalmovieinfo.exception.util.MyExceptionBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = {
        AdditionalInfoController.class,
        MyExceptionBuilder.class
    })
class AdditionalInfoControllerRequestTest  {
  @MockBean
  MovieAdditionalInfoPort movieAdditionalInfo;
  @Autowired
  private MockMvc mockMvc;

  @Test()
  void getAdditionalMovieDetails_Failure() throws Exception{
    //try{
    this.mockMvc.perform(
        get("/additionalInfos/{id}",12)
            .header("idType","normal"))
        .andExpect(status().isForbidden());
    //    }
//    catch (Exception e){
//
//    }
  }
  @Test
  void getAdditionalMovieDetails_Success() throws Exception{
    this.mockMvc.perform(
        get("/additionalInfos/{id}",1)
            .header("idType","normal"))
        .andExpect(status().isOk());
  }
}
