package org.example.tracuu.repository;

import org.example.tracuu.model.Nganh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NganhRepository extends JpaRepository<Nganh, Integer> {
    Optional<Nganh> findByManganh(String manganh);
}
