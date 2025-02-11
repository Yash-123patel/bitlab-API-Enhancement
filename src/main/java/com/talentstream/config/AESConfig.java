package com.talentstream.config;
 
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
 
@Component
public class AESConfig {
    @Value("${aes.secret.key}")
    private String secretKey;
 
    public String getSecretKey() {
        return secretKey;
    }
}