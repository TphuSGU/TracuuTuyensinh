package org.example.tracuu.repository;

import org.example.tracuu.model.NguyenVong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NguyenVongRepository extends JpaRepository<NguyenVong, Integer> {
    List<NguyenVong> findByCccdOrderByThuTuAsc(String cccd);
}
