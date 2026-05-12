package org.example.tracuu.repository;

import org.example.tracuu.model.NganhTohop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NganhTohopRepository extends JpaRepository<NganhTohop, Integer> {
    List<NganhTohop> findByManganh(String manganh);
}
