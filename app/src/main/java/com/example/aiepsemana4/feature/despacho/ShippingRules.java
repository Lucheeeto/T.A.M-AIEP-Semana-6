package com.example.aiepsemana4.feature.despacho;

public class ShippingRules {

    public static final int RADIO_GRATIS_KM = 20;
    public static final int TARIFA_MEDIA = 150;  // $/km
    public static final int TARIFA_ALTA  = 300;  // $/km
    public static final int UMBRAL_GRATIS = 50000;
    public static final int UMBRAL_MEDIA  = 25000;

    public static int calcularCostoDespacho(int montoCompra, int distanciaKm) {
        if (montoCompra >= UMBRAL_GRATIS) {
            if (distanciaKm <= RADIO_GRATIS_KM) return 0;
            return distanciaKm * TARIFA_MEDIA; // fuera del radio gratuito
        }
        if (montoCompra >= UMBRAL_MEDIA) return distanciaKm * TARIFA_MEDIA;
        return distanciaKm * TARIFA_ALTA;
    }

    public static String explicarRegla(int montoCompra, int distanciaKm) {
        if (montoCompra >= UMBRAL_GRATIS) {
            if (distanciaKm <= RADIO_GRATIS_KM)
                return "Compra ≥ 50.000 y distancia ≤ 20 km → despacho $0 (gratis).";
            else
                return "Compra ≥ 50.000 pero fuera de 20 km → $150/km.";
        } else if (montoCompra >= UMBRAL_MEDIA) {
            return "Compra entre 25.000 y 49.999 → $150/km.";
        } else {
            return "Compra < 25.000 → $300/km.";
        }
    }
}
