package com.simulation.domaine.medicament;

import java.util.Objects;

public class Medicament {
    private  String id;
    private final double h;

    public Medicament(String id, double h) {
        this.id = id;
        this.h = h;
    }

    public String getId() {
        return id;
    }

    public double getH() {
        return h;
    }

    @Override
    public String toString() {
        return "Medicament{" + id + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicament that = (Medicament) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
