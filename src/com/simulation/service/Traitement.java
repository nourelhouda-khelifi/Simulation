package com.simulation.service;

import com.simulation.domaine.medicament.Medicament;

import java.util.HashMap;
import java.util.Map;

public class Traitement {

    private final Map<Integer, Cycle> cycles = new HashMap<>();

    public void fixerDose(int numero, Medicament medicament, double dose) {
        Cycle cycle = cycles.computeIfAbsent(numero, n -> new Cycle(n, new HashMap<>()));
        cycle.getDoses().put(medicament, dose);
    }

    public Map<Medicament, Double> getDosesPourCycle(int numero) {
        Cycle cycle = cycles.get(numero);
        return cycle != null ? cycle.getDoses() : new HashMap<>();
    }

    public Cycle getCycle(int numero) {
        return cycles.get(numero);
    }
}
