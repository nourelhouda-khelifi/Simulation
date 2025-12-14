package com.simulation.domaine.pathogene;


import com.simulation.domaine.medicament.Medicament;

import java.util.*;


public abstract class Pathogene  {

    protected final double tauC;  
    protected final double alphaI; 
    private final Map<Medicament, Double> alphaParMedicament = new HashMap<>();
    private final Map<Medicament, Double> resistanceParMedicament = new HashMap<>();
    private final Map<Medicament, Double> deltaParMedicament = new HashMap<>();

    protected boolean IsQ;

    public Pathogene(double tauC, double alphaI, boolean isQ) { 
        this.tauC = tauC;
        this.alphaI = alphaI;
        this.IsQ = isQ;
    }


    public void setAlphaPourMedicament(Medicament medicament, double alphaM) {
        alphaParMedicament.put(medicament, alphaM);
    }
    public double getAlphaPourMedicament(Medicament medicament) {
        return alphaParMedicament.getOrDefault(medicament, 0.0);
    }

    public void setDeltaPourMedicament(Medicament medicament, double delta) {
        deltaParMedicament.put(medicament, delta);
    }
    public double getDeltaPourMedicament(Medicament medicament) {
        return deltaParMedicament.getOrDefault(medicament, 0.0);
    }

    public void setResistancePourMedicament(Medicament medicament, double r) {
        resistanceParMedicament.put(medicament, r);
    }
    public double getResistancePourMedicament(Medicament medicament) {
        return resistanceParMedicament.getOrDefault(medicament, 0.0);
    }

    public Map<Medicament, Double> getResistances() {
        return new HashMap<>(resistanceParMedicament);
    }

    public boolean isQ() {
        return IsQ;
    }

    /**
     * Somme Σ α_m * D_{m,t} * (1 - R_m)
     */
    public double calculEffetMedicaments(Map<Medicament, Double> concentrations) {
        double somme = 0.0;
        for (Map.Entry<Medicament, Double> e : concentrations.entrySet()) {
            Medicament med = e.getKey();
            double Dmt = e.getValue();
            double alphaM = getAlphaPourMedicament(med);
            double Rm = getResistancePourMedicament(med);
            somme += alphaM * Dmt * (1.0 - Rm);
        }
        return somme;
    }

    public abstract double calculerCharge(double L, double immunite, Map<Medicament, Double> conc);








}

