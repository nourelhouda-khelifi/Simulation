package com.simulation.domaine.patient;

import com.simulation.domaine.medicament.Concentration;
import com.simulation.domaine.medicament.Medicament;
import com.simulation.domaine.pathogene.Pathogene;
import com.simulation.domaine.pathogene.PathogenePatient;
import com.simulation.snapshot.PatientSnapshot;

import java.util.*;

public abstract class Patient {

    protected  String id;
    protected double immunite;
    private final double beta;
    private final double f;

    protected final List<PathogenePatient> pathogenes = new ArrayList<>();
    protected final Map<Medicament, Concentration> concentrations = new HashMap<>();
    protected final List<PatientSnapshot> historique = new ArrayList<>();

    // gamma : sensibilité de l'immunité à la somme des pathogènes immuno-dépressifs (γ)
    protected double gamma = 0.0;

    public Patient(String id, double immuniteInitiale, double beta, double f) {
        if (id == null) throw new NullPointerException("L'ID patient ne peut pas être null");
        if (beta < 0) throw new IllegalArgumentException("Beta doit être positif ou zéro " );
        if (f < 0) throw new IllegalArgumentException("F (fatigue) doit être positif ou zéro"  );
        
        this.id = id;
        this.immunite = Math.max(0.0, immuniteInitiale);
        this.beta = beta;
        this.f = f;
    }

    public Map<Medicament, Concentration> getConcentrations() {
        return concentrations;
    }

    public void setGamma(double gamma) {
        if (gamma < 0 || gamma > 1.0) throw new IllegalArgumentException("Gamma doit être entre 0 et 1, reçu : " + gamma);
        this.gamma = gamma;
    }

    public double getGamma() {
        return gamma;
    }

    public void ajouterPathogene(Pathogene p) {
        if (p != null) pathogenes.add(new PathogenePatient(p));
    }

    public List<PathogenePatient> getPathogenes() {
        return Collections.unmodifiableList(pathogenes);
    }

    public void ajouterConcentration(Concentration c) {
        Objects.requireNonNull(c, "La concentration ne peut pas être null");
        concentrations.put(c.getMedicament(), c);
    }

    public Concentration getConcentration(Medicament medicament) {
        return concentrations.get(medicament);
    }

    public double getImmunite() {
        return immunite;
    }

    public double getF() {
        return f;
    }

    public double getBeta() {
        return beta;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return id;
    }

    public void evoluerCycle(int cycle, Map<Medicament, Double> doses) {
        
        // 1 - mettre à jour concentrations DABORD (D_{t+1} = h * D_t + d_{m,t})
        for (Map.Entry<Medicament, Concentration> e : concentrations.entrySet()) {
            Medicament med = e.getKey();
            Concentration conc = e.getValue();
            double dose = doses.getOrDefault(med, 0.0);
            conc.updateAvecDose(dose);
        }

        // 2 - Récupérer les concentrations MISES A JOUR
        Map<Medicament, Double> concValues = new HashMap<>();
        for (Medicament med : concentrations.keySet()) {
            Concentration conc = concentrations.get(med);
            concValues.put(med, conc.getValeur());
        }

        // 3 - faire evoluer chaque pathogene (utilise I_t et D_{m,t+1})
        for (PathogenePatient pp : pathogenes) {
            pp.evoluerCycle(cycle, immunite, concValues);
        }

        // 4 - calculer Somme L_{p,t+1} et Somme L_{q,t+1} (q = ceux avec estImmunoDepresseur==true)
        double sommeCharges = 0.0;
        double sommeDepresseurs = 0.0;

        for (PathogenePatient pp : pathogenes) {
            double Lp = pp.getCharge();
            sommeCharges += Lp;
            if (pp.isQ()) sommeDepresseurs += Lp;
        }

        // 5 - calculer I_{t+1} : délégation aux sous-classes (formules 4,5,6) + appliquer -gamma * sommeDepresseurs
        double nouvelleI = calculerNouvelleImmunite(this.immunite, sommeCharges, sommeDepresseurs);
        this.immunite = Math.max(0.0, nouvelleI);

        // 6 - historiser l'état patient
        Map<Medicament, Double> concSnapshot = new HashMap<>();
        for (Map.Entry<Medicament, Concentration> e : concentrations.entrySet()) {
            concSnapshot.put(e.getKey(), e.getValue().getValeur());
        }
        historique.add(new PatientSnapshot(cycle, immunite, concSnapshot));
    }

    protected abstract double calculerNouvelleImmunite(double I_t, double sommeCharges, double sommeDepresseurs);

    public List<PatientSnapshot> getHistorique() {
        return Collections.unmodifiableList(historique);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", immunite=" + immunite +
                ", beta=" + beta +
                ", f=" + f +
                ", pathogenes=" + pathogenes +
                ", concentrations=" + concentrations +
                ", historique=" + historique +
                ", gamma=" + gamma +
                '}';
    }
}
