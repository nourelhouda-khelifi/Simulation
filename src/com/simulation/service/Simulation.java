package com.simulation.service;

import com.simulation.domaine.medicament.Medicament;
import com.simulation.domaine.patient.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Simulation {

    private final List<Patient> patients = new ArrayList<>();
    private final Traitement traitement;
    private final int nbCycles;

    private final List<Cycle> cycles = new ArrayList<>();

    public Simulation(Traitement traitement, int nbCycles) {
        this.traitement = traitement;
        this.nbCycles = nbCycles;
    }

    public void ajouterPatient(Patient p) {
        patients.add(p);
    }

    public List<Cycle> getCycles() {
        return cycles;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    //applique ces doses sur tous les patients, et chaque patient fait évoluer ses pathogènes et son immunité
    public void run() {

        
        // Boucle sur tous les cycles
        for (int c = 0; c < nbCycles; c++) {
            // Récupérer les doses pour ce cycle (maintenant avec Medicament)
            Map<Medicament, Double> doses = traitement.getDosesPourCycle(c);
            Cycle cycle = new Cycle(c, doses);
            cycles.add(cycle);
            // Faire évoluer chaque patient pour ce cycle
            for (Patient p : patients) {
                p.evoluerCycle(c, doses);
            }
        }
    }


}
