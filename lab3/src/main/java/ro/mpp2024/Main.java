package ro.mpp2024;

import ro.mpp2024.domain.Honey;
import ro.mpp2024.repository.HoneyDbRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        HoneyDbRepository repo = new HoneyDbRepository();
        System.out.println("Start");
        // 1. Add a new record
        Honey newHoney = new Honey(
                "Manuka",      // Sortiment
                "500g",     // Cantitate
                0.25,       // Concentratie_polen_la_suta
                34.5,       // Concentratie_glucoza_la_suta
                39.2        // Concentratie_fructoza_la_suta
        );
        repo.add(newHoney);
        System.out.println("Added new honey: " + newHoney);

        newHoney.setCantitate("1kg");
        newHoney.setConcentratiePolenLaSuta(0.30);
        repo.update(newHoney);
        System.out.println("Updated honey with sortiment=Tei");


        List<Honey> filtered = repo.findByPolenGreaterThan(0.2);
        System.out.println("Honey with polen > 0.2:");
        for (Honey h : filtered) {
            System.out.println(h);
        }

        // 4. Optionally, print all
        System.out.println("\nAll honey rows in DB:");
        List<Honey> all = repo.findAll();
        for (Honey h : all) {
            System.out.println(h);
        }
    }
}
