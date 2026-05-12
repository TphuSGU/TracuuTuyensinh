package org.example.tracuu.repository;

import org.example.tracuu.model.TohopMonthi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TohopMonthiRepository extends JpaRepository<TohopMonthi, Integer> {
    Optional<TohopMonthi> findByMatohop(String matohop);
}
