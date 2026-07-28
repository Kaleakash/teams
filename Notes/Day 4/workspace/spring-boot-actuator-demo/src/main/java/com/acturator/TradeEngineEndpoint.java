package com.acturator;

import com.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "tradeengine") // Accessible via: /actuator/tradeengine
public class TradeEngineEndpoint {
    @Autowired
    ProductRepository productRepository;
    private String engineStatus = "RUNNING"; // Mutable status for simulation

    /**
     * GET Operation: http://localhost:8080/actuator/tradeengine
     * Returns operational business and system metrics.
     */
    @ReadOperation
    public Map<String, Object> getEngineMetrics() {
        Map<String, Object> metrics = new HashMap<>();

        // 1. App Business State
        metrics.put("engine_status", engineStatus);
        metrics.put("total_processed_products", productRepository.count());

        // 2. System JVM Memory Check
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        metrics.put("jvm_memory_usage_pct", String.format("%.2f%%", (double) (totalMemory - freeMemory) / totalMemory * 100));

        // 3. Simple threshold alert flag
        if (freeMemory < (totalMemory * 0.1)) {
            metrics.put("system_alert", "CRITICAL_LOW_MEMORY");
        } else {
            metrics.put("system_alert", "NONE");
        }

        return metrics;
    }

    /**
     * POST Operation: Send a POST request to http://localhost:8080/actuator/tradeengine
     * Allows DevOps teams to dynamically change components state at runtime.
     */
    @WriteOperation
    public Map<String, String> configureEngineStatus(String newStatus) {
        Map<String, String> response = new HashMap<>();
        this.engineStatus = newStatus.toUpperCase();
        response.put("message", "Trade engine status updated successfully");
        response.put("updated_status", this.engineStatus);
        return response;
    }
}
