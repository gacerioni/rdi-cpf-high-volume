package io.platformengineer.rdicpfhighvolume.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPooled;

import io.platformengineer.rdicpfhighvolume.utils.CSVDataLoaderService;
import io.platformengineer.rdicpfhighvolume.utils.IndexCreationUtility;

@Component
public class RedisInitializationService implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private IndexCreationUtility indexCreationUtility;
    @Autowired
    private CSVDataLoaderService csvDataLoaderService;

    @Value("${redis.uri}")
    private String redisUri;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try (JedisPooled jedis = new JedisPooled(redisUri)) {
            // Flush all data from Redis
            jedis.flushAll();
            System.out.println("Redis data flushed.");

            // Create indexes
            indexCreationUtility.createIndexes();

            // Load CSV data
            csvDataLoaderService.loadCSVData();
        }
    }
}
