package com.example.aiepsemana4;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.aiepsemana4.domain.Vehicle;
import com.example.aiepsemana4.feature.conversion.AngleConverter;
import com.example.aiepsemana4.feature.despacho.ShippingRules;
import com.example.aiepsemana4.feature.vehiculo.VehicleSummary;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AppDebug";

    // ---- GPS ----
    private static final int LOCATION_PERMISSION_REQUEST = 100;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView tvUbicacion;
    private Button btnUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // -------- Sección A: Conversión a radianes --------
        EditText etDegrees = findViewById(R.id.etDegrees);
        Button btnConvert = findViewById(R.id.btnConvert);
        TextView tvRadians = findViewById(R.id.tvRadians);

        btnConvert.setOnClickListener(v -> {
            String gradosStr = etDegrees.getText().toString().trim();
            if (!gradosStr.matches("-?\\d+(\\.\\d+)?")) {
                Toast.makeText(this, "Ingresa un número válido en grados.", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                double grados = Double.parseDouble(gradosStr);
                double rad = AngleConverter.degreesToRadians(grados);
                tvRadians.setText("Radianes: " + rad);
                Log.d(TAG, "RADIANES=" + rad);
            } catch (Exception e) {
                tvRadians.setText("Radianes: —");
                Toast.makeText(this, "Error en conversión.", Toast.LENGTH_SHORT).show();
            }
        });

        // -------- Sección B: Resumen de vehículo --------
        EditText etMarca = findViewById(R.id.etMarca);
        EditText etModelo = findViewById(R.id.etModelo);
        EditText etAnio = findViewById(R.id.etAnio);
        EditText etCilindrada = findViewById(R.id.etCilindrada);
        Button btnResumen = findViewById(R.id.btnResumenVehiculo);
        TextView tvResumen = findViewById(R.id.tvResumenVehiculo);

        btnResumen.setOnClickListener(v -> {
            try {
                String marca = etMarca.getText().toString().trim();
                if (!marca.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                    Toast.makeText(this, "La marca debe contener solo letras.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String modelo = etModelo.getText().toString().trim();
                if (!modelo.matches("[a-zA-Z0-9\\s]+")) {
                    Toast.makeText(this, "El modelo debe ser texto o números válidos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String anioStr = etAnio.getText().toString().trim();
                if (!anioStr.matches("\\d+")) {
                    Toast.makeText(this, "El año debe contener solo números.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int anio = Integer.parseInt(anioStr);

                String cilStr = etCilindrada.getText().toString().trim();
                if (!cilStr.matches("\\d+(\\.\\d+)?")) {
                    Toast.makeText(this, "La cilindrada debe ser un número decimal.", Toast.LENGTH_SHORT).show();
                    return;
                }
                double cilindrada = Double.parseDouble(cilStr);

                Vehicle veh = new Vehicle(marca, modelo, anio, cilindrada);
                String resumen = VehicleSummary.build(veh);

                tvResumen.setText(resumen);
                Log.d(TAG, "RESUMEN_VEHICULO=" + resumen);

            } catch (Exception e) {
                tvResumen.setText("Resumen: —");
                Toast.makeText(this, "Completa todos los campos correctamente.", Toast.LENGTH_SHORT).show();
            }
        });

        // -------- Sección C: Cálculo de despacho --------
        EditText etMonto = findViewById(R.id.etMontoCompra);
        EditText etDist = findViewById(R.id.etDistanciaKm);
        Button btnDespacho = findViewById(R.id.btnCalcularDespacho);
        TextView tvDespacho = findViewById(R.id.tvResultadoDespacho);

        btnDespacho.setOnClickListener(v -> {
            String montoStr = etMonto.getText().toString().trim();
            String kmStr = etDist.getText().toString().trim();

            if (!montoStr.matches("\\d+")) {
                Toast.makeText(this, "El monto debe contener solo números.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!kmStr.matches("\\d+")) {
                Toast.makeText(this, "La distancia debe contener solo números.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int monto = Integer.parseInt(montoStr);
                int km = Integer.parseInt(kmStr);

                int costo = ShippingRules.calcularCostoDespacho(monto, km);
                String regla = ShippingRules.explicarRegla(monto, km);

                String texto = "Costo de despacho: $" + costo + " (" + regla + ")";
                tvDespacho.setText(texto);
                Log.d(TAG, "DESPACHO=" + texto);

            } catch (Exception e) {
                tvDespacho.setText("Costo de despacho: —");
                Toast.makeText(this, "Error en cálculo de despacho.", Toast.LENGTH_SHORT).show();
            }
        });

        // -------- Sección D: Alarma de temperatura --------
        EditText etTempActual = findViewById(R.id.etTempActual);
        EditText etTempLimite = findViewById(R.id.etTempLimite);
        Button btnVerif = findViewById(R.id.btnVerificarTemp);
        TextView tvAlarma = findViewById(R.id.tvAlarma);

        btnVerif.setOnClickListener(v -> {
            String tActStr = etTempActual.getText().toString().trim();
            String tLimStr = etTempLimite.getText().toString().trim();

            if (!tActStr.matches("-?\\d+(\\.\\d+)?")) {
                Toast.makeText(this, "La temperatura actual debe ser un número (puede ser negativo).", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!tLimStr.matches("-?\\d+(\\.\\d+)?")) {
                Toast.makeText(this, "El límite debe ser un número (puede ser negativo).", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double tAct = Double.parseDouble(tActStr);
                double tLim = Double.parseDouble(tLimStr);

                if (tAct > tLim) {
                    String msg = "ALERTA: temperatura (" + tAct + "°C) supera límite (" + tLim + "°C)";
                    tvAlarma.setText(msg);
                    tvAlarma.setTextColor(0xFFB00020); // rojo
                    Log.w(TAG, msg);
                } else {
                    String msg = "OK: temperatura dentro de rango.";
                    tvAlarma.setText(msg);
                    tvAlarma.setTextColor(0xFF008000); // verde
                    Log.d(TAG, msg);
                }
            } catch (Exception e) {
                tvAlarma.setText("Alarma: —");
                Toast.makeText(this, "Error en verificación de temperatura.", Toast.LENGTH_SHORT).show();
            }
        });

        // -------- Sección E: Ubicación GPS --------
        tvUbicacion = findViewById(R.id.tvUbicacion);
        btnUbicacion = findViewById(R.id.btnObtenerUbicacion);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnUbicacion.setOnClickListener(v -> solicitarYObtenerUbicacion());
    }

    private void solicitarYObtenerUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST
            );
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();
                        tvUbicacion.setText("Lat: " + lat + ", Lon: " + lon);
                    } else {
                        Toast.makeText(MainActivity.this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                solicitarYObtenerUbicacion();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
