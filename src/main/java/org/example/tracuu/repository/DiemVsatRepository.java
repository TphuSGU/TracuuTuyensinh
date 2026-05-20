package org.example.tracuu.repository;

import org.example.tracuu.model.DiemVsat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiemVsatRepository extends JpaRepository<DiemVsat, Integer> {
    Optional<DiemVsat> findByCccd(String cccd);
}
