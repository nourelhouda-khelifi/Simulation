package com.simulation.domaine.pathogene;

import com.simulation.comportement.IComportementCharge;
import com.simulation.domaine.medicament.Medicament;
import com.simulation.snapshot.StateSnapshot;

import java.util.*;


public abstract class Pathogene implements IComportementCharge {

    protected double L;           // L_t
    protected final double tauC;  // τc
    protected final double alphaI; // αi :sensibilité à l'immunité

    // sensibilité alpha_m par Medicament (objet au lieu de String)
    private final Map<Medicament, Double> alphaParMedicament = new HashMap<>();
    // résistance R_{m,t}
    private final Map<Medicament, Double> resistanceParMedicament = new HashMap<>();
    // delta_m pour évolution de la résistance (équation 3)
    private final Map<Medicament, Double> deltaParMedicament = new HashMap<>();

    protected boolean IsQ;

    protected final List<StateSnapshot> historique = new ArrayList<>();

    public Pathogene(double L0, double tauC, double alphaI, boolean isQ) {
        if (L0 < 0) throw new IllegalArgumentException("L0 (charge initiale) ne peut pas être négatif " );
        if (tauC < 0) throw new IllegalArgumentException("TauC  ne peut pas être négatif " + tauC);
        if (alphaI < 0) throw new IllegalArgumentException("AlphaI (sensibilité immunitaire) ne peut pas être négatif " );
        
        this.L = L0;
        this.tauC = tauC;
        this.alphaI = alphaI;
        this.IsQ = isQ;
    }


    public void setAlphaPourMedicament(Medicament medicament, double alphaM) {
        if (medicament == null) throw new IllegalArgumentException("Le médicament ne peut pas être null");
        if (alphaM < 0) throw new IllegalArgumentException("Alpha (sensibilité au médicament) ne peut pas être négatif, reçu : " + alphaM);
        alphaParMedicament.put(medicament, alphaM);
    }
    public double getAlphaPourMedicament(Medicament medicament) {
        return alphaParMedicament.getOrDefault(medicament, 0.0);
    }

    public void setDeltaPourMedicament(Medicament medicament, double delta) {
        if (medicament == null) throw new IllegalArgumentException("Le médicament ne peut pas être null");
        if (delta < 0) throw new IllegalArgumentException("Delta (résistance) ne peut pas être négatif, reçu : " + delta);
        deltaParMedicament.put(medicament, delta);
    }
    public double getDeltaPourMedicament(Medicament medicament) {
        return deltaParMedicament.getOrDefault(medicament, 0.0);
    }

    public void setResistancePourMedicament(Medicament medicament, double r) {
        Objects.requireNonNull(medicament, "Le médicament ne peut pas être null");
        if (r < 0 || r > 1.0) throw new IllegalArgumentException("Résistance doit être entre 0 et 1, " );
        resistanceParMedicament.put(medicament, r);
    }
    public double getResistancePourMedicament(Medicament medicament) {
        return resistanceParMedicament.getOrDefault(medicament, 0.0);
    }

    public Map<Medicament, Double> getResistances() {
        return new HashMap<>(resistanceParMedicament);
    }

    public double getCharge() {
        return L;
    }

    public boolean isQ() {
        return IsQ;
    }

    /**
     * Somme Σ α_m * D_{m,t} * (1 - R_m)
     * concentrations : Map<Medicament, Double>
     */
    protected double calculEffetMedicaments(Map<Medicament, Double> concentrations) {
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

    public void evoluerCycle(int cycle, double immunite, Map<Medicament, Double> conc) {
        // 1 calcul de la charge
        this.L = calculerCharge(immunite, conc);

        // mise à jour résistance si sous-classe résistant
        if (this instanceof com.simulation.comportement.IResistant resistant) {
            resistant.mettreAJourResistance(conc);
        }

        // enregistrement historique
        historique.add(
                new StateSnapshot(cycle, L, new HashMap<>(resistanceParMedicament), new HashMap<>(conc)));
    }

    public abstract double calculerCharge(double immunite, Map<Medicament, Double> conc);







    public List<StateSnapshot> getHistorique() {
        return Collections.unmodifiableList(historique);
    }
}

