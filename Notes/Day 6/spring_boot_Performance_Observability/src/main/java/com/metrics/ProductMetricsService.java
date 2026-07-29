package com.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ProductMetricsService {

    // Counter
    private final Counter productCreatedCounter;

    // Timer
    private final Timer productFetchTimer;

    // Distribution Summary
    private final DistributionSummary productPriceSummary;

    // Gauge
    private final AtomicInteger totalProducts = new AtomicInteger();

    public ProductMetricsService(MeterRegistry meterRegistry) {

        // Counter
        this.productCreatedCounter = Counter.builder("products.created")
                .description("Total number of products created")
                .register(meterRegistry);

        // Timer
        this.productFetchTimer = Timer.builder("products.fetch.time")
                .description("Time taken to fetch products")
                .register(meterRegistry);

        // Distribution Summary
        this.productPriceSummary = DistributionSummary.builder("products.price")
                .description("Statistics of product prices")
                .baseUnit("rupees")
                .register(meterRegistry);

        // Gauge
        Gauge.builder("products.total",
                        totalProducts,
                        AtomicInteger::get)
                .description("Current total number of products")
                .register(meterRegistry);
    }

    // ==========================
    // Counter
    // ==========================

    public void incrementProductCreated() {
        productCreatedCounter.increment();
    }

    // ==========================
    // Timer
    // ==========================

    public void recordFetchTime(long timeInMillis) {
        productFetchTimer.record(timeInMillis, TimeUnit.MILLISECONDS);
    }

    // ==========================
    // Distribution Summary
    // ==========================

    public void recordProductPrice(double price) {
        productPriceSummary.record(price);
    }

    // ==========================
    // Gauge
    // ==========================

    public void updateTotalProducts(int total) {
        totalProducts.set(total);
    }

}
