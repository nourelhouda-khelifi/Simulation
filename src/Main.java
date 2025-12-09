import com.simulation.domaine.pathogene.PathogeneClassique;
import com.simulation.domaine.patient.PatientAdulte;
import com.simulation.domaine.medicament.Medicament;
import com.simulation.domaine.medicament.Concentration;
import com.simulation.service.Simulation;
import com.simulation.service.Traitement;

public class Main {

    public static void main(String[] args) {
        
        
        // 1) Créer le patient adulte
        PatientAdulte patient = new PatientAdulte("Patient_Adulte", 1.0, 0.4, 0.1);
        System.out.println("Patient créé: " + patient.getId() + " (Immunité initiale: 1.0)");
        
        // 2) Créer le médicament avec h = 0.8
        Medicament medicament = new Medicament("Med_Standard", 0.8);
        System.out.println("Médicament créé: " + medicament.getId() + " (h = 0.8)");
        
        // 3) Créer le pathogène classique
        PathogeneClassique pathogene = new PathogeneClassique(10.0, 0.3, 0.2, false);
        // Configurer la résistance initiale au médicament
        pathogene.setResistancePourMedicament(medicament, 0.1);
        // Configurer la sensibilité du pathogène au médicament
        pathogene.setAlphaPourMedicament(medicament, 0.5);
        System.out.println("Pathogène créé: L0=10, τc=0.3, alphai=0.2, Rm=0.1");
        
        // 4) Ajouter le pathogène au patient
        patient.ajouterPathogene(pathogene);
        
        // 5) Ajouter une concentration initiale du médicament
        Concentration concentration = new Concentration(medicament, 0.0);
        patient.ajouterConcentration(concentration);
        
        // 6) Créer le plan de traitement
        Traitement traitement = new Traitement();
        // Doses de 1 tous les 3 cycles (cycles 0, 3, 6, 9, 12, 15, 18)
        for (int cycle = 0; cycle < 20; cycle += 3) {
            traitement.fixerDose(cycle, medicament, 1.0);
        }
        System.out.println("Traitement configuré: dose 1 tous les 3 cycles");
        
        // 7) Créer et exécuter la simulation
        Simulation simulation = new Simulation(traitement, 20);
        simulation.ajouterPatient(patient);
        simulation.run();
        
        // 8) Afficher les résultats des snapshots
        afficherResultats(patient);
    }
    
    private static void afficherResultats(PatientAdulte patient) {
        System.out.println("Patient: " + patient.getId());
        System.out.println("\nHistorique du patient (Immunité et Concentrations):");
        System.out.println(String.format("%-8s %-15s", "Cycle", "Immunité"));
        System.out.println("-".repeat(23));
        
        for (var snapshot : patient.getHistorique()) {
            System.out.println(String.format("%-8d %-15.4f", snapshot.getCycle(), snapshot.getImmunite()));
        }
        
        System.out.println("\n\nHistorique des pathogènes (Charge et Résistances):");
        for (int i = 0; i < patient.getPathogenes().size(); i++) {
            var pathogenePatient = patient.getPathogenes().get(i);
            System.out.println("\nPathogène " + (i + 1) + ":");
            System.out.println(String.format("%-8s %-15s %-20s", "Cycle", "Charge", "Résistance"));
            System.out.println("-".repeat(45));
            
            for (var snapshot : pathogenePatient.getHistorique()) {
                String resistances = snapshot.getResistances().isEmpty() ? "N/A" : 
                    String.format("%.4f", snapshot.getResistances().values().stream().findFirst().orElse(0.0));
                System.out.println(String.format("%-8d %-15.4f %-20s", 
                    snapshot.getCycle(), snapshot.getCharge(), resistances));
            }
        }
    }
    }





    
