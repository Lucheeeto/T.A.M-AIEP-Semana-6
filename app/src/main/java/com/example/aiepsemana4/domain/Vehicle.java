package com.example.aiepsemana4.domain;

public class Vehicle {
    private final String marca;
    private final String modelo;
    private final int anio;
    private final double cilindrada;

    public Vehicle(String marca, String modelo, int anio, double cilindrada) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.cilindrada = cilindrada;
    }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAnio() { return anio; }
    public double getCilindrada() { return cilindrada; }
}
