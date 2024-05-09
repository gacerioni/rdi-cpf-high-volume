package io.platformengineer.rdicpfhighvolume.utils;

import io.platformengineer.rdicpfhighvolume.initialization.RedisDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPooled;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class DatabaseCleanupService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RedisDataService redisDataService;

    @Value("${redis.uri}")
    private String redisUri;

    @Transactional
    public void cleanupDatabase() {
        cleanupMySQL();
        redisDataService.initializeRedis();  // Reset Redis data
    }

    private void cleanupMySQL() {
        // You might still want to control foreign key checks to avoid issues during truncate
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE vehicle").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE address").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE person").executeUpdate();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
    }

    private void cleanupRedis() {
        try (JedisPooled jedis = new JedisPooled(redisUri)) {
            // Flush all data from Redis
            jedis.flushAll();
            System.out.println("Redis data flushed as part of cleanup.");
        } catch (Exception e) {
            System.err.println("Failed to flush Redis: " + e.getMessage());
        }
    }
}
