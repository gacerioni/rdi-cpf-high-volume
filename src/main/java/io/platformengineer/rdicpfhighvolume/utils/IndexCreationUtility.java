package io.platformengineer.rdicpfhighvolume.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.IndexDefinition;
import redis.clients.jedis.search.IndexOptions;
import redis.clients.jedis.search.Schema;
import jakarta.annotation.PostConstruct;

@Component
public class IndexCreationUtility {

    static private String indexName = "io.platformengineer.rdicpfhighvolume.vehicle.VehicleRedisIdx";

    @Value("${redis.uri}")
    private String redisUri;

    @PostConstruct
    public void createIndexes() {
        try (JedisPooled jedis = new JedisPooled(redisUri)) {

            // Flush all data from Redis before creating the index
            jedis.flushAll();

            // Define the schema based on the provided structure
            Schema schema = new Schema()
                    .addTextField("$.first_name", 1.0)
                    .addTextField("$.last_name", 1.0)
                    .addTextField("$.email", 1.0)
                    .addTextField("$.address.*.city", 1.0)
                    .addTextField("$.address.*.state", 1.0)
                    .addTextField("$.address.*.country", 1.0)
                    .addTextField("$.address.*.street", 1.0)
                    .addTextField("$.vehicles.*.model", 1.0).as("model")
                    .addTextField("$.vehicles.*.plate", 1.0).as("plate")
                    .addTagField("$.vehicles.*.color", ",").as("color")
                    .addGeoField("$.vehicles.*.location").as("location");

            // Define the index options with prefix to target only specific keys
            IndexOptions options = IndexOptions.defaultOptions()
                    .setDefinition(new IndexDefinition(IndexDefinition.Type.JSON)
                            .setPrefixes("person:"));

            // Create the index
            jedis.ftCreate(indexName, options, schema);

            System.out.println("Index created successfully.");
        } catch (Exception e) {
            System.err.println("Failed to create index: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
