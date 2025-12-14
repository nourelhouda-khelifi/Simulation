package com.simulation.domaine.medicament;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Medicament {
    private   List<String> existingIds = new ArrayList<>();
    private  String id;
    private final double h;

    public Medicament(String id, double h) {
        if (existingIds.contains(id)) {
            throw new IllegalArgumentException("Un médicament avec l'ID '" + id + "' existe déjà");
        }
        this.id = id;
        this.h = h;
        existingIds.add(id);
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
