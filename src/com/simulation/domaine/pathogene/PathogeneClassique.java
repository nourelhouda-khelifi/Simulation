package com.simulation.domaine.pathogene;

import com.simulation.domaine.medicament.Medicament;

import java.util.Map;

public class PathogeneClassique extends Pathogene {
    public PathogeneClassique(double tauC, double alphaI, boolean isQ) {
        super(tauC, alphaI, isQ);
    }

    @Override
    public double calculerCharge(double L, double immunite, Map<Medicament, Double> conc) {
        return calculerChargeClassique(L, immunite, conc);
    }

    protected double calculerChargeClassique(double L, double immunite, Map<Medicament, Double> conc) {
        double croissance = tauC * L;
        double effetImm = alphaI * immunite;
        double effetMed = calculEffetMedicaments(conc);

        return Math.max(0.0, L + croissance - effetImm - effetMed);
    }
}
