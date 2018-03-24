package com.roport.microservices.lamaria.utils;

import com.roport.microservices.lamaria.utils.RoporCrypto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Configuration
public class CustomEncryptorBootstrapConfiguration {

    @Bean
    public TextEncryptor textEncryptor() {
        try {
            return new RoporCrypto();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
