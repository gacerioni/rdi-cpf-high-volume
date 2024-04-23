package io.platformengineer.rdicpfhighvolume.harnessfeatureflag;

import io.harness.cf.client.api.CfClient;
import io.harness.cf.client.api.FeatureFlagInitializeException;
import io.harness.cf.client.dto.Target;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeatureFlagManager {

    private final CfClient cfClient;
    private final Target defaultTarget;

    //@Value("${harness.ff.api-key}")
    private String apiKey;

    @Value("${mysql.latency.min.flagname}")
    private String mysqlLatencyMinFlagName;

    @Value("${mysql.latency.max.flagname}")
    private String mysqlLatencyMaxFlagName;

    public FeatureFlagManager() {
        this.apiKey = "fea0beac-ef19-433c-b08c-bf4bb3e06641";
        this.cfClient = new CfClient(apiKey);
        try {
            cfClient.waitForInitialization();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Thread was interrupted during feature flag client initialization.", e);
        } catch (FeatureFlagInitializeException e) {
            throw new IllegalStateException("Failed to initialize feature flags client.", e);
        }

        this.defaultTarget = Target.builder()
                .identifier("Gabs_RDI_Demo_EKS")
                .name("RDI Demo EKS")
                .build();
    }

    public int getMinLatency() {
        double defaultMinLatency = 100;  // Set the default minimum latency
        double latency = cfClient.numberVariation(mysqlLatencyMinFlagName, defaultTarget, defaultMinLatency);
        System.out.println("Min latency: " + latency);
        return (int) Math.round(latency);  // Convert double to int with rounding
    }

    public int getMaxLatency() {
        double defaultMaxLatency = 1000;  // Set the default maximum latency
        double latency = cfClient.numberVariation(mysqlLatencyMaxFlagName, defaultTarget, defaultMaxLatency);
        System.out.println("Max latency: " + latency);
        return (int) Math.round(latency);  // Convert double to int with rounding
    }

    public void close() {
        if (cfClient != null) {
            cfClient.close();
        }
    }
}
