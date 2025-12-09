package com.simulation.domaine.patient;

public class PatientAdulte extends Patient {

    public PatientAdulte(String id, double immuniteInitiale, double beta, double f) {
        super(id, immuniteInitiale, beta, f);

    }


    @Override
    protected double calculerNouvelleImmunite(double I_t, double sommeCharges, double sommeDepresseurs) {

        // 1)  β * Σ(L_p,t+1)
        double activation = getBeta() * sommeCharges;

        // f * I_t
        double fatigue = getF() * I_t;

        // (pathogènes Q)
        double effetDepresseur = getGamma() * sommeDepresseurs;

        //  formule
        double I_next = I_t + activation - fatigue - effetDepresseur;

        return Math.max(0.0, I_next);
    }

}
