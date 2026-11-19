package com.demoblaze.automationtest.runners;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KarateApiRunner {

    @Test
    void testRunner() {
        String tag = System.getProperty("karate.filter.tags", "@api");

        Results results = Runner
                .path("classpath:karate")
                .tags(tag)
                .outputCucumberJson(true)
                .parallel(2);

        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }
}
