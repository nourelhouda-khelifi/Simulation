package com.simulation.domaine.medicament;

import java.util.Objects;

//état de la concentration D_{m,t} pour un patient
public class Concentration {
    private  Medicament medicament;   //référence au médicament
    private double valeur;  //D_{m,t}

    public Concentration(Medicament medicament, double valeurInitiale) {

       if (medicament == null) throw new NullPointerException("Le médicament ne peut pas être null");
       if (valeurInitiale < 0) throw new IllegalArgumentException("La concentration ne peut pas être négative" );
       this.medicament = medicament;
       this.valeur = valeurInitiale;
    }

    public String getMedId() {
        return medicament.getId();
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public double getValeur() {
        return valeur;
    }

    //on applique l'équation (9) : met à jour la concentration pour le pas suivant
    public void updateAvecDose(double dose) {
        this.valeur = medicament.getH() * this.valeur + dose;
        if (this.valeur < 0) this.valeur = 0;
    }
}
