package com.simulation.domaine.pathogene;

import com.simulation.domaine.medicament.Medicament;
import com.simulation.snapshot.StateSnapshot;

import java.util.*;
public class PathogenePatient {
    
    private  Pathogene pathogene;          
    private double L;                           
    private final List<StateSnapshot> historique = new ArrayList<>(); 
    
    public PathogenePatient(Pathogene pathogene) {
        this.pathogene = pathogene;
        this.L = 0;  
    }
    public PathogenePatient(Pathogene pathogene , double L0) {
        this.pathogene = pathogene;
        this.L = 0;  
    }
    
    
    public void evoluerCycle(int cycle, double immunite, Map<Medicament, Double> conc) {
        this.L = pathogene.calculerCharge(L, immunite, conc);
        if (pathogene instanceof com.simulation.comportement.IResistant resistant) {
            resistant.mettreAJourResistance(conc);
        }
        historique.add(new StateSnapshot(cycle, L, new HashMap<>(pathogene.getResistances()), new HashMap<>(conc)));
    }
    
    public double getCharge() {
        return L;
    }

    public void setCharge(double L) {
        this.L = L;
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
