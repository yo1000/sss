// src/main/java/io/github/yo1000/sss/config/CacheConfiguration.java
package io.github.yo1000.sss.config;

import io.github.yo1000.sss.model.Memo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class CacheConfiguration {
    public static class CacheStoreMap<K, V> extends ConcurrentHashMap<K, V> {}

    @Bean
    public CacheStoreMap<String, Memo> cacheStoreMap() {
        return new CacheStoreMap<>();
    }
}
