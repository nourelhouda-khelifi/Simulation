package com.simulation.domaine.pathogene;

import com.simulation.comportement.IAgressif;
import com.simulation.comportement.IResistant;
import com.simulation.domaine.medicament.Medicament;

import java.util.Map;

public class PathogeneAgressifResistant extends Pathogene implements IAgressif, IResistant {
    public PathogeneAgressifResistant(double tauC, double alphaI, boolean isQ) {
        super(tauC, alphaI, isQ);
    }

    @Override
    public double calculerCharge(double L, double immunite, Map<Medicament, Double> conc) {
        return calculerChargeAgressive(L, tauC, immunite, calculEffetMedicaments(conc));
    }

    @Override
    public double calculerChargeAgressive(double L, double tauC, double immunite, double effetMed) {
        double croissance = tauC * (L * L);
        double effetImm = alphaI * immunite;

        return Math.max(0, L + croissance - effetImm - effetMed);
    }

    @Override
    public void mettreAJourResistance(Map<Medicament, Double> concentrations) {
        for (var e : concentrations.entrySet()) {
            Medicament med = e.getKey();
            double delta = getDeltaPourMedicament(med);
            double oldR = getResistancePourMedicament(med);
            double newR =  oldR + delta * e.getValue();  
            setResistancePourMedicament(med, newR);
        }
    }
}
