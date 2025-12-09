package com.simulation.domaine.patient;

public class PatientAge  extends Patient{

    public PatientAge(String id, double immuniteInitiale, double beta, double f) {
        super(id, immuniteInitiale, beta, f);

    }

    @Override
    protected double calculerNouvelleImmunite(double I_t, double sommeCharges, double sommeDepresseurs) {

        // Activation normale : β * ΣL
        double activation = getBeta() * sommeCharges;

        // Fatigue quadratique
        double fatigue = getF() * (I_t * I_t);

        // Dépression immunitaire
        double effetDepresseur = getGamma() * sommeDepresseurs;

        double I_next = I_t + activation - fatigue - effetDepresseur;

        return Math.max(0.0, I_next);
    }

}
