import com.simulation.domaine.pathogene.*;
import com.simulation.service.Simulation;
import com.simulation.service.Traitement;
import com.simulation.domaine.medicament.Concentration;
import com.simulation.domaine.medicament.Medicament;
import com.simulation.domaine.patient.Patient;
import com.simulation.domaine.patient.PatientAdulte;
import com.simulation.domaine.patient.PatientAge;
import com.simulation.domaine.patient.PatientJeune;
import com.simulation.snapshot.PatientSnapshot;
import com.simulation.snapshot.StateSnapshot;
import java.util.List;

public class Main {

    public static void main(String[] args) {
       
        System.out.println(">>> SCENARIO 1: Patient Sain vs Pathogène Classique\n");
        scenario1_PatientSainSimple();
        
        System.out.println("\n>>> SCENARIO 2: Patient Adulte vs Pathogènes Mixtes\n");
        scenario2_PatientAdulteMixte();
        
        System.out.println("\n>>> SCENARIO 3: Patient Jeune vs Pathogènes Agressifs\n");
        scenario3_PatientJeuneAgressif();
        
    }





    
    private static void scenario1_PatientSainSimple() {
        
        Medicament medic = new Medicament("Ampicilline", 0.7);
        
        // Traitement : dosage régulier pendant 4 cycles
        Traitement traitement = new Traitement();
        traitement.fixerDose(0, medic, 2.0);
        traitement.fixerDose(1, medic, 2.0);
        traitement.fixerDose(2, medic, 1.5);
        traitement.fixerDose(3, medic, 1.0);
        
        Simulation simulation = new Simulation(traitement, 4);
        
        
        Patient patient = new PatientAdulte("Patient_Sain", 20.0, 0.4, 0.2);
        
        
        PathogeneClassique patho = new PathogeneClassique(15.0, 0.15, 0.08, false);
        patho.setAlphaPourMedicament(medic, 0.7); 
        
        patient.ajouterPathogene(patho);
        patient.ajouterConcentration(new Concentration(medic, 0.0));
        simulation.ajouterPatient(patient);
        
        simulation.run();
        afficherHistoriqueSimulation("SCENARIO 1 : Cas Simple", simulation);
    }
    
   
    private static void scenario2_PatientAdulteMixte() {
        
        Medicament medA = new Medicament("Peniciline", 0.7);
        Medicament medB = new Medicament("Tetracycline", 0.65);
        
        
        Traitement traitement = new Traitement();
        traitement.fixerDose(0, medA, 1.5);
        traitement.fixerDose(1, medA, 1.8);
        traitement.fixerDose(2, medA, 1.8);
        traitement.fixerDose(3, medA, 1.5);
        traitement.fixerDose(3, medB, 1.2);
        traitement.fixerDose(4, medB, 1.5);
        
        Simulation simulation = new Simulation(traitement, 5);
        
       
        Patient patient = new PatientAdulte("Patient_Adulte", 18.0, 0.35, 0.25);
        
        
        PathogeneClassique patho1 = new PathogeneClassique(12.0, 0.12, 0.10, false);
        patho1.setAlphaPourMedicament(medA, 0.6);
        patho1.setAlphaPourMedicament(medB, 0.65);
        
       
        PathogeneAgressif patho2 = new PathogeneAgressif(8.0, 0.08, 0.15, false);
        patho2.setAlphaPourMedicament(medA, 0.5);
        patho2.setAlphaPourMedicament(medB, 0.55);
        
        patient.ajouterPathogene(patho1);
        patient.ajouterPathogene(patho2);
        patient.ajouterConcentration(new Concentration(medA, 0.0));
        patient.ajouterConcentration(new Concentration(medB, 0.0));
        simulation.ajouterPatient(patient);
        
        simulation.run();
        afficherHistoriqueSimulation("SCENARIO 2 : Pathogènes Mixtes", simulation);
    }
    
   
    private static void scenario3_PatientJeuneAgressif() {
        
        Medicament medX = new Medicament("Cephalosporine", 0.75);
        Medicament medY = new Medicament("Fluoroquinolone", 0.70);
        
       
        Traitement traitement = new Traitement();
        traitement.fixerDose(0, medX, 2.0);
        traitement.fixerDose(0, medY, 1.5);
        traitement.fixerDose(1, medX, 2.0);
        traitement.fixerDose(1, medY, 1.5);
        traitement.fixerDose(2, medX, 1.5);
        traitement.fixerDose(2, medY, 1.2);
        traitement.fixerDose(3, medX, 1.0);
        traitement.fixerDose(3, medY, 1.0);
        
        Simulation simulation = new Simulation(traitement, 4);
        
        
        Patient patient = new PatientJeune("Patient_Jeune", 25.0, 0.5, 0.10);
        
        
        PathogeneAgressifResistant patho = new PathogeneAgressifResistant(18.0, 0.05, 0.18, true);
        
        
        patho.setAlphaPourMedicament(medX, 0.45);
        patho.setDeltaPourMedicament(medX, 0.09);
        patho.setResistancePourMedicament(medX, 0.20);  // Développe résistance
        
        patho.setAlphaPourMedicament(medY, 0.50);
        patho.setDeltaPourMedicament(medY, 0.11);
        patho.setResistancePourMedicament(medY, 0.18);  // Développe aussi résistance
        
        patient.ajouterPathogene(patho);
        patient.ajouterConcentration(new Concentration(medX, 0.0));
        patient.ajouterConcentration(new Concentration(medY, 0.0));
        simulation.ajouterPatient(patient);
        
        simulation.run();
        afficherHistoriqueSimulation("SCENARIO 3 : Résistance Élevée", simulation);
    }
    

    
    

    private static void afficherHistoriqueSimulation(String nomScenario, Simulation simulation) {
        System.out.println(" HISTORIQUE: " + nomScenario  );
        
        for (Patient patient : simulation.getPatients()) {
            System.out.println("\n--- Patient: " + patient.getNom() );
            afficherHistoriquePatient(patient);
        }
        
        System.out.println("FIN " + nomScenario );
    }

    private static void afficherHistoriquePatient(Patient patient) {
        List<PatientSnapshot> snapshots = patient.getHistorique();
        
        if (snapshots == null || snapshots.isEmpty()) {
            System.out.println("Pas d'historique disponible.");
            return;
        }
        
        for (PatientSnapshot snapshot : snapshots) {
            System.out.println("\n  Cycle " + snapshot.getCycle() + ":");
            System.out.println("    Immunité: " + String.format("%.4f", snapshot.getImmunite()));
            System.out.println("    Concentrations:");
            
            if (snapshot.getConcentrations().isEmpty()) {
                System.out.println("      (aucune)");
            } else {
                for (Medicament med : snapshot.getConcentrations().keySet()) {
                    double conc = snapshot.getConcentrations().get(med);
                    System.out.println("      " + med.getId() + ": " + String.format("%.4f", conc));
                }
            }
            
            afficherHistoriquePathogenes(patient, snapshot.getCycle());
        }
    }

    private static void afficherHistoriquePathogenes(Patient patient, int cycle) {
        List<com.simulation.domaine.pathogene.PathogenePatient> pathogenes = patient.getPathogenes();
        
        if (pathogenes == null || pathogenes.isEmpty()) {
            return;
        }
        
        System.out.println("    Pathogènes:");
        for (com.simulation.domaine.pathogene.PathogenePatient pathoPatient : pathogenes) {
            List<com.simulation.snapshot.StateSnapshot> stateSnapshots = pathoPatient.getHistorique();
            
            if (stateSnapshots != null) {
                for (com.simulation.snapshot.StateSnapshot stateSnap : stateSnapshots) {
                    if (stateSnap.getCycle() == cycle) {
                        System.out.println("      Charge: " + String.format("%.4f", stateSnap.getCharge()));
                        System.out.println("      Résistances:");
                        
                        if (stateSnap.getResistances().isEmpty()) {
                            System.out.println("        (aucune)");
                        } else {
                            for (Medicament med : stateSnap.getResistances().keySet()) {
                                double res = stateSnap.getResistances().get(med);
                                System.out.println("        " + med.getId() + ": " + String.format("%.4f", res));
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

}


