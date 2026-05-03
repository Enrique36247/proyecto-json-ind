package com.example;

import java.util.List;

/**
 * Contenedor para almacenar la lista de Robots
 */
public class DatosLaboratorio {
    private List<Robot> robots;

    public DatosLaboratorio() {
    }

    public DatosLaboratorio(List<Robot> robots) {
        this.robots = robots;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public void setRobots(List<Robot> robots) {
        this.robots = robots;
    }
}