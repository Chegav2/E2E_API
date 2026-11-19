package com.demoblaze.automationtest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public final class PurchaseDataReader {

    private PurchaseDataReader() {
    }

    public static Map<String, String> load(String caseId) {
        try (InputStream input = PurchaseDataReader.class.getClassLoader()
                .getResourceAsStream("data/e2e/purchase_cases.csv")) {
            if (input == null) {
                throw new IllegalStateException("No se encontro data/e2e/purchase_cases.csv");
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(input, StandardCharsets.UTF_8))) {
                String[] headers = reader.readLine().split(",");
                return reader.lines()
                        .map(line -> line.split(",", -1))
                        .filter(values -> values.length > 0 && values[0].equals(caseId))
                        .findFirst()
                        .map(values -> {
                            Map<String, String> data = new LinkedHashMap<>();
                            for (int i = 0; i < headers.length; i++) {
                                data.put(headers[i], values[i]);
                            }
                            return data;
                        })
                        .orElseThrow(() -> new IllegalArgumentException(
                                "No existe el caso de compra: " + caseId));
            }
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudo leer purchase_cases.csv", exception);
        }
    }
}
