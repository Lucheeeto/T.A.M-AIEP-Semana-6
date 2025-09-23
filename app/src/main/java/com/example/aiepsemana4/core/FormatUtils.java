package com.example.aiepsemana4.core;

public class FormatUtils {
    public static String normalize(String raw) {
        if (raw == null) return "";
        return raw.trim().replace(',', '.');
    }
    public static double parseDoubleStrict(String raw) throws NumberFormatException {
        String n = normalize(raw);
        if (n.isEmpty()) throw new NumberFormatException("vacío");
        return Double.parseDouble(n);
    }
    public static int parseIntStrict(String raw) throws NumberFormatException {
        String n = normalize(raw);
        if (n.isEmpty()) throw new NumberFormatException("vacío");
        return Integer.parseInt(n);
    }
}
