package com.simulation.snapshot;

import com.simulation.domaine.medicament.Medicament;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
public class PatientSnapshot {
    private  int cycle;
    private  double immunite;
    private  Map<Medicament, Double> concentrations;

    public PatientSnapshot(int cycle, double immunite, Map<Medicament, Double> concentrations) {
        this.cycle = cycle;
        this.immunite = immunite;
        this.concentrations = Collections.unmodifiableMap(new HashMap<>(concentrations));
    }

    public int getCycle() { return cycle; }
    public double getImmunite() { return immunite; }
    public Map<Medicament, Double> getConcentrations() { return concentrations; }
}
