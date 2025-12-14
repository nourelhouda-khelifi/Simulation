import com.simulation.domaine.pathogene.PathogeneClassique;
import com.simulation.domaine.patient.PatientAdulte;
import com.simulation.domaine.medicament.Medicament;
import com.simulation.domaine.medicament.Concentration;
import com.simulation.service.Simulation;
import com.simulation.service.Traitement;

public class Main {

    public static void main(String[] args) {
        
       
        PatientAdulte patient = new PatientAdulte("Patient_Adulte", 1.0, 0.4, 0.1);
        System.out.println("Patient 1 " + patient.getId() + " (Immunité initiale: 1.0)");
        
        Medicament medicament = new Medicament("Med_Standard", 0.8);
        System.out.println("Médicament 1" + medicament.getId() + " (h = 0.8)");
        
    
        PathogeneClassique pathogene = new PathogeneClassique(0.3, 0.2, false);
        pathogene.setResistancePourMedicament(medicament, 0.1);
        pathogene.setAlphaPourMedicament(medicament, 0.5);
        System.out.println("Pathogène créé: TAUX=0.3, alphai=0.2, Rm=0.1");
        
      
        patient.ajouterPathogene(pathogene, 10.0);
        
    
        Concentration concentration = new Concentration(medicament, 0.0);
        patient.ajouterConcentration(concentration);
        
        
        Traitement traitement = new Traitement();
        for (int cycle = 0; cycle < 20; cycle += 3) {
            traitement.fixerDose(cycle, medicament, 1.0);
        }
        System.out.println("dose 1 tous les 3 cycles");
        
    
        Simulation simulation = new Simulation(traitement, 20);
        simulation.ajouterPatient(patient);
        simulation.run();
        
      
        afficherResultats(patient);
    }
    
    private static void afficherResultats(PatientAdulte patient) {
        System.out.println("Patient: " + patient.getId());
        System.out.println("\nHistorique du patient (Immunité et Concentrations):");
        System.out.println(String.format("%-8s %-15s %-20s", "Cycle", "Immunité", "Concentrations"));
        System.out.println("-".repeat(65));
        
        for (var snapshot : patient.getHistorique()) {
            String conc = snapshot.getConcentrations().isEmpty() ? "N/A" : 
                String.format("%.4f", snapshot.getConcentrations().values().stream().findFirst().orElse(0.0));
            System.out.println(String.format("%-8d %-15.4f %-20s", snapshot.getCycle(), snapshot.getImmunite(), conc));
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





    
