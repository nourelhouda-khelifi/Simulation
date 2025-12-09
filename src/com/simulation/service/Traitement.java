package com.simulation.service;

import com.simulation.domaine.medicament.Medicament;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*pour chaque cycle (clé = numéro du cycle), on stocke un map Medicament → dose.*/
public class Traitement {

    // cycle -> (Medicament -> dose)
    private final Map<Integer, Map<Medicament, Double>> plan = new HashMap<>();

    public void fixerDose(int cycle, Medicament medicament, double dose) {

        plan.computeIfAbsent(cycle, c -> new HashMap<>()).put(medicament, dose);
    }

    public Map<Medicament, Double> getDosesPourCycle(int cycle) {
        return plan.getOrDefault(cycle, new HashMap<>());
    }
}
