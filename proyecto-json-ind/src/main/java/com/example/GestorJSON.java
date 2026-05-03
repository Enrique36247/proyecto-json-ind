package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de persistencia JSON para el laboratorio de robots
 * Maneja lectura, escritura y validación del archivo JSON
 * 
 */
public class GestorJSON {
    private static final String NOMBRE_ARCHIVO = "laboratorio.json";
    private final Gson gson;
    private final Path rutaArchivo;

    public GestorJSON() {
        // Configurar Gson con formato legible
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        
        // Usar el directorio actual de trabajo
        this.rutaArchivo = Paths.get(NOMBRE_ARCHIVO);
    }

    /**
     * Carga los datos desde el archivo JSON
     * @return Lista de robots o lista vacía si no existe el archivo
     */
    public List<Robot> cargarDatos() {
        try {
            if (!Files.exists(rutaArchivo)) {
                System.out.println("El archivo no existe. Se creará uno nuevo.");
                return new ArrayList<>();
            }

            String contenido = Files.readString(rutaArchivo);
            
            if (contenido == null || contenido.trim().isEmpty()) {
                return new ArrayList<>();
            }

            DatosLaboratorio datos = gson.fromJson(contenido, DatosLaboratorio.class);
            
            if (datos != null && datos.getRobots() != null) {
                return datos.getRobots();
            }
            
            return new ArrayList<>();
            
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error al parsear el JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Guarda los datos en el archivo JSON
     * @param robots Lista de robots a guardar
     * @return true si se guardó correctamente, false en caso de error
     */
    public boolean guardarDatos(List<Robot> robots) {
        try {
            DatosLaboratorio datos = new DatosLaboratorio(robots);
            String json = gson.toJson(datos);
            
            Files.writeString(rutaArchivo, json);
            System.out.println("Datos guardados correctamente en " + rutaArchivo.toAbsolutePath());
            return true;
            
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error al generar el JSON: " + e.getMessage());
            return false;
        }
    }

    /**
     * Valida los datos de un robot
     * @param robot Robot a validar
     * @return Mensaje de error o null si es válido
     */
    public String validarRobot(Robot robot) {
        if (robot == null) {
            return "El robot no puede ser null";
        }
        
        if (robot.getId() == null || robot.getId().trim().isEmpty()) {
            return "El ID es obligatorio";
        }
        
        if (robot.getNombre() == null || robot.getNombre().trim().isEmpty()) {
            return "El nombre es obligatorio";
        }
        
        if (robot.getModelo() == null || robot.getModelo().trim().isEmpty()) {
            return "El modelo es obligatorio";
        }
        
        if (robot.getFuncion() == null || robot.getFuncion().trim().isEmpty()) {
            return "La función es obligatoria";
        }
        
        if (robot.getAñoFabricacion() < 1900 || robot.getAñoFabricacion() > 2100) {
            return "El año de fabricación debe estar entre 1900 y 2100";
        }
        
        if (robot.getEstado() == null || robot.getEstado().trim().isEmpty()) {
            return "El estado es obligatorio";
        }
        
        // Validar que el estado sea uno de los permitidos
        String estado = robot.getEstado().toUpperCase();
        if (!estado.equals("ACTIVO") && !estado.equals("MANTENIMIENTO") && !estado.equals("INACTIVO")) {
            return "El estado debe ser: ACTIVO, MANTENIMIENTO o INACTIVO";
        }
        
        if (robot.getUbicacion() == null || robot.getUbicacion().trim().isEmpty()) {
            return "La ubicación es obligatoria";
        }
        
        return null; // Válido
    }

    /**
     * Genera un nuevo ID único para un robot
     * @param robots Lista de robots existente
     * @return Nuevo ID único
     */
    public String generarNuevoId(List<Robot> robots) {
        int maxId = 0;
        for (Robot r : robots) {
            try {
                int idNum = Integer.parseInt(r.getId().replace("R", ""));
                if (idNum > maxId) {
                    maxId = idNum;
                }
            } catch (NumberFormatException e) {
                // Ignorar IDs que no sigan el formato
            }
        }
        return "R" + String.format("%04d", maxId + 1);
    }

    /**
     * Obtiene la ruta del archivo JSON
     * @return Ruta absoluta del archivo
     */
    public String getRutaArchivo() {
        return rutaArchivo.toAbsolutePath().toString();
    }
}