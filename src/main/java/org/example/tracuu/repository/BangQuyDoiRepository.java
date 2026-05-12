package org.example.tracuu.repository;

import org.example.tracuu.model.BangQuyDoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BangQuyDoiRepository extends JpaRepository<BangQuyDoi, Integer> {
    List<BangQuyDoi> findByPhuongThuc(String phuongThuc);
    List<BangQuyDoi> findByPhuongThucAndMon(String phuongThuc, String mon);
}
