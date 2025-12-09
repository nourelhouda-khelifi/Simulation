package com.simulation.domaine.patient;

public class PatientJeune extends Patient {
    public PatientJeune(String id, double immuniteInitiale, double beta, double f) {
        super(id,  immuniteInitiale, beta, f);

    }

    @Override
    protected double calculerNouvelleImmunite(double I_t, double sommeCharges, double sommeDepresseurs) {

        //  β * sqrt(ΣL)
        double activation = getBeta() * Math.sqrt(sommeCharges);

        // Fatigue
        double fatigue = getF() * I_t;

        // Effet  Q
        double effetDepresseur = getGamma() * sommeDepresseurs;

        double I_next = I_t + activation - fatigue - effetDepresseur;

        return Math.max(0.0, I_next);
    }

}
