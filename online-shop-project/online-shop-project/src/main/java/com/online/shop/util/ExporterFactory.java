package com.online.shop.util;

public class ExporterFactory {
    public static Exporter getExporter(String type) {
        return switch (type.toLowerCase()) {
            case "xml" -> new DbToXml();
            case "json" -> new DbToJson();
            default -> throw new IllegalArgumentException("Unknown exporter type: " + type);
        };
    }
}
