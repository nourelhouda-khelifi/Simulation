package com.simulation.comportement;

import com.simulation.domaine.medicament.Medicament;

import java.util.Map;

public interface IResistant {

    void mettreAJourResistance(Map<Medicament, Double> concentrations);
}
