package com.simulation.domaine.pathogene;

import com.simulation.domaine.medicament.Medicament;
import com.simulation.snapshot.StateSnapshot;

import java.util.*;

/**
  associe un pathogène partagé à sa charge L SPÉCIFIQUE pour UN PATIENT.
 * 
 * Le pathogène lui-même (paramètres tauC, alphaI, resistances) est PARTAGÉ entre patients.
 * Seule la charge L change par patient.
 * L'historique est aussi local au patient.
 */
public class PathogenePatient {
    
    private final Pathogene pathogene;          
    private double L;                           
    private final List<StateSnapshot> historique = new ArrayList<>(); 
    
    public PathogenePatient(Pathogene pathogene) {
        this.pathogene = pathogene;
        this.L = pathogene.getCharge();  // Initialiser avec la charge du pathogène
    }
    
    
    public void evoluerCycle(int cycle, double immunite, Map<Medicament, Double> conc) {
        double chargeCalculee = pathogene.calculerCharge(immunite, conc);
        
        this.L = chargeCalculee;
        
        historique.add(new StateSnapshot(cycle, L, new HashMap<>(pathogene.getResistances()), new HashMap<>(conc)));
    }
    
    // GETTERS
    public double getCharge() {
        return L;
    }
    
    public List<StateSnapshot> getHistorique() {
        return Collections.unmodifiableList(historique);
    }
    
    public Pathogene getPathogene() {
        return pathogene;
    }
    
    public double getAlphaPourMedicament(Medicament med) {
        return pathogene.getAlphaPourMedicament(med);
    }
    
    public double getResistancePourMedicament(Medicament med) {
        return pathogene.getResistancePourMedicament(med);
    }
    
    public boolean isQ() {
        return pathogene.isQ();
    }
}
