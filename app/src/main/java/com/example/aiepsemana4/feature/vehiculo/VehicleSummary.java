package com.example.aiepsemana4.feature.vehiculo;

import com.example.aiepsemana4.domain.Vehicle;

public class VehicleSummary {
    public static String build(Vehicle v) {
        return "Vehículo: " + v.getMarca() + " " + v.getModelo()
                + " (" + v.getAnio() + "), " + v.getCilindrada() + "L";
    }
}
