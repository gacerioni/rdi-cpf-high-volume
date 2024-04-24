package io.platformengineer.rdicpfhighvolume.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.search.IndexDefinition;
import redis.clients.jedis.search.IndexOptions;
import redis.clients.jedis.search.Schema;
import jakarta.annotation.PostConstruct;

@Component
public class IndexCreationUtility {

    static private String indexName = "io.platformengineer.rdicpfhighvolume.vehicle.VehicleRedisIdx";

    @Value("${redis.uri}")
    private String redisUri;

    public void createIndexes() {
        try (JedisPooled jedis = new JedisPooled(redisUri)) {

            // Attempt to retrieve information about the index to check if it already exists
            try {
                jedis.ftInfo(indexName);
                System.out.println("Index already exists.");
                return; // Exit if index exists
            } catch (JedisDataException e) {
                System.out.println("Index does not exist, creating new index.");
            }
            // Define the schema based on the provided structure
            Schema schema = new Schema()
                    .addTextField("$.first_name", 1.0).as("first_name")
                    .addTextField("$.last_name", 1.0).as("last_name")
                    .addTextField("$.email", 1.0).as("email")
                    .addTextField("$.address.*.street", 1.0).as("street")  // Adjusted path
                    .addTextField("$.address.*.city", 1.0).as("city")       // Adjusted path
                    .addTextField("$.address.*.state", 1.0).as("state")     // Adjusted path
                    .addTextField("$.address.*.country", 1.0).as("country") // Adjusted path
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
