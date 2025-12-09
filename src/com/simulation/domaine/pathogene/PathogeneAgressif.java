package com.simulation.domaine.pathogene;

import com.simulation.comportement.IAgressif;
import com.simulation.domaine.medicament.Medicament;

import java.util.Map;

public class PathogeneAgressif extends Pathogene implements IAgressif {

    public PathogeneAgressif(double L0, double tauC, double alphaI, boolean isQ) {
        super(L0, tauC, alphaI, isQ);
    }

    @Override
    public double calculerCharge(double immunite, Map<Medicament, Double> conc) {
        return calculerChargeAgressive(L, tauC, immunite, calculEffetMedicaments(conc));
    }

    @Override
    public double calculerChargeAgressive(double L, double tauC, double immunite, double effetMed) {
        double croissance = tauC * (L * L); // agressif
        double effetImm = alphaI * immunite;

        return Math.max(0, L + croissance - effetImm - effetMed);
    }
}
