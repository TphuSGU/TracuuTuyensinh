package org.example.tracuu.repository;

import org.example.tracuu.model.ThiSinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThiSinhRepository extends JpaRepository<ThiSinh, Long> {

    Optional<ThiSinh> findBySoBaoDanh(String soBaoDanh);

    List<ThiSinh> findByHoTenContainingIgnoreCase(String hoTen);

    List<ThiSinh> findByNganhXetTuyenContainingIgnoreCase(String nganhXetTuyen);

    List<ThiSinh> findByKetQua(String ketQua);
}
