package ro.mpp2024.repository;

import ro.mpp2024.domain.Honey;
import java.util.List;

public interface HoneyRepository {
    void add(Honey honey);
    void update(Honey honey);
    List<Honey> findAll();
    List<Honey> findByPolenGreaterThan(double threshold);
}

