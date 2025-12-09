package com.simulation.domaine.pathogene;

import com.simulation.comportement.IResistant;
import com.simulation.domaine.medicament.Medicament;

import java.util.Map;

public class PathogeneResistant extends Pathogene implements IResistant {
    public PathogeneResistant(double L0, double tauC, double alphaI, boolean isQ) {
        super(L0, tauC, alphaI, isQ);
    }

    @Override
    public double calculerCharge(double immunite, Map<Medicament, Double> conc) {
        return calculerChargeClassique(immunite, conc);
    }

    @Override
    public void mettreAJourResistance(Map<Medicament, Double> conc) {
        for (var e : conc.entrySet()) {
            Medicament med = e.getKey();
            double delta = getDeltaPourMedicament(med);
            double oldR = getResistancePourMedicament(med);
            double newR = Math.min(1.0, oldR + delta * e.getValue());  // ← Cap à 1.0
            setResistancePourMedicament(med, newR);
        }
    }

    // Formule classique (1)
    protected double calculerChargeClassique(double immunite, Map<Medicament, Double> conc) {
        double croissance = tauC * L;
        double effetImm = alphaI * immunite;
        double effetMed = calculEffetMedicaments(conc);

        return Math.max(0.0, L + croissance - effetImm - effetMed);
    }
}
