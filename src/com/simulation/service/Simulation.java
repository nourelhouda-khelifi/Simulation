package com.simulation.service;

import com.simulation.domaine.medicament.Medicament;
import com.simulation.domaine.patient.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public void run() {
        
        // Boucle cycles
        for (int c = 0; c < nbCycles; c++) {
            System.out.println("CYCLE " + c);
            // Récupérer les doses pour ce cycle
            Map<Medicament, Double> doses = traitement.getDosesPourCycle(c);
            System.out.println("Doses pour ce cycle: " + doses);
            Cycle cycle = new Cycle(c, doses);
            cycles.add(cycle);
            // Faire évoluer chaque patient pour ce cycle
            for (Patient p : patients) {
                p.evoluerCycle(c, doses);
            }
        }
    }


}
