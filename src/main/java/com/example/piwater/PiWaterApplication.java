package com.example.piwater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
//@EnableJms
@SpringBootApplication
public class PiWaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiWaterApplication.class, args);
    
    }
/*
    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setErrorHandler(t -> {
            System.out.println("Error in listener!" + t);
        });
        configurer.configure(factory, connectionFactory);
        return factory;
    }*/
}
