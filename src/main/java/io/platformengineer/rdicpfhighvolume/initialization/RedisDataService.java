package io.platformengineer.rdicpfhighvolume.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;
import io.platformengineer.rdicpfhighvolume.utils.IndexCreationUtility;

@Service
public class RedisDataService {

    @Autowired
    private IndexCreationUtility indexCreationUtility;

    @Autowired
    private ControlledDataInitializationService controlledDataInitializationService;

    @Value("${redis.uri}")
    private String redisUri;

    public void initializeRedis() {
        try (JedisPooled jedis = new JedisPooled(redisUri)) {
            jedis.flushAll();
            System.out.println("Redis data flushed.");

            indexCreationUtility.createIndexes();
            System.out.println("Indexes created.");

            controlledDataInitializationService.insertControlledData();
            System.out.println("Controlled data inserted.");
        }
    }
}