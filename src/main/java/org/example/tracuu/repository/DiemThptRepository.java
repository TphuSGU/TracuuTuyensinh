package org.example.tracuu.repository;

import org.example.tracuu.model.DiemThpt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiemThptRepository extends JpaRepository<DiemThpt, Integer> {
    Optional<DiemThpt> findByCccd(String cccd);
}
