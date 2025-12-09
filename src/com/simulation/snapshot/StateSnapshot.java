package com.simulation.snapshot;

import com.simulation.domaine.medicament.Medicament;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
//garder un état d’un pathogène à un moment donné (cycle t)
public class StateSnapshot {

    private final int cycle;
    private final double charge;
    private final Map<Medicament, Double> resistances;
    private final Map<Medicament, Double> concentrations;

    public StateSnapshot(int cycle, double charge, Map<Medicament, Double> resistances, Map<Medicament, Double> concentrations) {
        this.cycle = cycle;
        this.charge = charge;
        this.resistances = Collections.unmodifiableMap(new HashMap<>(resistances));
        this.concentrations = Collections.unmodifiableMap(new HashMap<>(concentrations));
    }

    public int getCycle() { return cycle; }
    public double getCharge() { return charge; }
    public Map<Medicament, Double> getResistances() { return resistances; }
    public Map<Medicament, Double> getConcentrations() { return concentrations; }
}
