package com.Springfield.springbootMultiThreading.config;


import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {

  @Bean (name = "taskExecutor")
  public Executor taskExecutor () {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor ();
    executor.setCorePoolSize (2);
    executor.setQueueCapacity (100);
    executor.setMaxPoolSize (2);
    executor.setThreadNamePrefix ("customThread");
    executor.initialize ();
    return executor;
  }
}
