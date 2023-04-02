package com.example.newswebsite.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = null;
        Map config = new HashMap();
        config.put("cloud_name", "dqugpsayz");
        config.put("api_key", "781412127951145");
        config.put("api_secret", "kfZdTfR4MIf7ReyQke-9fRfpfRk");
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }
}
