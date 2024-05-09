package io.platformengineer.rdicpfhighvolume.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPooled;

import io.platformengineer.rdicpfhighvolume.utils.CSVDataLoaderService;
import io.platformengineer.rdicpfhighvolume.utils.IndexCreationUtility;
import io.platformengineer.rdicpfhighvolume.initialization.RedisDataService;

@Component
public class RedisInitializationService implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RedisDataService redisDataService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        redisDataService.initializeRedis();
        System.out.println("##### RedisInitializationService completed. Redis data flushed, indexes created, and controlled data inserted.");
        /*
        try (JedisPooled jedis = new JedisPooled(redisUri)) {
            // Flush all data from Redis
            jedis.flushAll();
            System.out.println("Redis data flushed by RedisInitializationService.");

            // Create indexes
            indexCreationUtility.createIndexes();
            System.out.println("Indexes created by RedisInitializationService.");

            controlledDataInitializationService.insertControlledData();
            System.out.println("Controlled data inserted by RedisInitializationService.");

            System.out.println(
                    "##### RedisInitializationService completed. Redis data flushed, indexes created, and controlled data inserted."
            );

            // Load CSV data - disabled because I'll do this via the API. This reader is causing too much problem in PROD.
            // csvDataLoaderService.loadCSVData();
        }*/
    }
}
