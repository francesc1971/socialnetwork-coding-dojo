/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Blueknow Labs
 *
 * (c) Copyright 2009-2019 Blueknow, S.L.
 *
 * ALL THE RIGHTS ARE RESERVED
 */
package com.blueknow.labs.network.springboot.config;

import com.blueknow.labs.network.port.in.PublishMessageUseCase;
import com.blueknow.labs.network.port.out.MessageRepository;
import com.blueknow.labs.network.service.PublishMessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocialNetworkConfiguration {

    @Bean
    PublishMessageUseCase publishMessageUseCase(final MessageRepository repository) {
        return new PublishMessageService (repository);
    }

}