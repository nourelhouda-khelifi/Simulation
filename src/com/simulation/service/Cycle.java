package com.simulation.service;

import com.simulation.domaine.medicament.Medicament;

import java.util.Map;

public class Cycle {

    private final int numero;
    private final Map<Medicament, Double> doses;

    public Cycle(int numero, Map<Medicament, Double> doses) {
        this.numero = numero;
        this.doses = doses;
    }

    public int getNumero() { return numero; }
    public Map<Medicament, Double> getDoses() { return doses; }
}
