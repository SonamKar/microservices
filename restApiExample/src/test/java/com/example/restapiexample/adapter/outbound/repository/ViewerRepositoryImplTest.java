package com.example.restapiexample.adapter.outbound.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.restapiexample.core.dbentity.Viewer;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.ribbon.FeignRibbonClientAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Component.class))
@ImportAutoConfiguration({
    RibbonAutoConfiguration.class,
    FeignRibbonClientAutoConfiguration.class,
    FeignAutoConfiguration.class
})
public class ViewerRepositoryImplTest {
  @Autowired
  ViewerRepositoryImpl viewerRepository;

  @Test
  void getViewerDetailsTest(){
    List<Viewer> viewerList=viewerRepository.getViewerDetails("viewer1");
    assertThat(viewerList.get(0).getAge()).isEqualTo(22);
  }
}
