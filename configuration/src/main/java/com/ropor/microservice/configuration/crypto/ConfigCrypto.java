package com.ropor.microservice.configuration.crypto;

import com.roport.microservices.lamaria.utils.RoporCrypto;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ConfigCrypto extends RoporCrypto {

    public ConfigCrypto() throws Exception{
        super();
    }
}
