package com.example.additionalmovieinfo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Target({ElementType.TYPE,ElementType.METHOD})
@SpringBootApplication
@Retention(RetentionPolicy.RUNTIME)
public @interface StcSpringBootApplication {

}
