package com.blch;


import com.blch.config.ConfigureQuartz;
import com.blch.dao.CustomMethodsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.WebApplicationInitializer;

import javax.annotation.PreDestroy;

@SpringBootApplication
@Import({ConfigureQuartz.class})
public class Application extends SpringBootServletInitializer{

  private static final Logger logger1 = LoggerFactory.getLogger(Application.class);


  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
    return application.sources(Application.class);
  }

 /* @Bean
  public CustomMethodsImpl ObjectCustomMethodsImpl() {
    return new CustomMethodsImpl();
  }*/


  //enalbe this scope to using the customized 404 page
  @Bean
  public ErrorPageRegistrar errorPageRegistrar(){
    return new MyErrorPageRegistrar();
  }

  private static class MyErrorPageRegistrar implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
      registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
    }

  }


}
