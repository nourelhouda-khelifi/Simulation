package com.simulation.snapshot;

import com.simulation.domaine.medicament.Medicament;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
//garder un état du patient à un moment donné (cycle t)
public class PatientSnapshot {
    private final int cycle;
    private final double immunite;
    private final Map<Medicament, Double> concentrations;

    public PatientSnapshot(int cycle, double immunite, Map<Medicament, Double> concentrations) {
        this.cycle = cycle;
        this.immunite = immunite;
        this.concentrations = Collections.unmodifiableMap(new HashMap<>(concentrations));
    }

    public int getCycle() { return cycle; }
    public double getImmunite() { return immunite; }
    public Map<Medicament, Double> getConcentrations() { return concentrations; }
}
