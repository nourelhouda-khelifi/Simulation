package com.simulation.snapshot;

import com.simulation.domaine.medicament.Medicament;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
public class StateSnapshot {

    private  int cycle;
    private  double charge;
    private  Map<Medicament, Double> resistances;
    private  Map<Medicament, Double> concentrations;

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
