package org.example.tracuu.service;

import org.example.tracuu.model.ThiSinh;
import org.example.tracuu.repository.ThiSinhRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThiSinhService {

    private final ThiSinhRepository thiSinhRepository;

    public ThiSinhService(ThiSinhRepository thiSinhRepository) {
        this.thiSinhRepository = thiSinhRepository;
    }

    public Optional<ThiSinh> timTheoSoBaoDanh(String soBaoDanh) {
        return thiSinhRepository.findBySoBaoDanh(soBaoDanh);
    }

    public List<ThiSinh> timTheoHoTen(String hoTen) {
        return thiSinhRepository.findByHoTenContainingIgnoreCase(hoTen);
    }

    public List<ThiSinh> timTheoNganh(String nganh) {
        return thiSinhRepository.findByNganhXetTuyenContainingIgnoreCase(nganh);
    }

    public List<ThiSinh> layTatCa() {
        return thiSinhRepository.findAll();
    }

    public ThiSinh luu(ThiSinh thiSinh) {
        return thiSinhRepository.save(thiSinh);
    }

    public void xoa(Long id) {
        thiSinhRepository.deleteById(id);
    }

    public Optional<ThiSinh> timTheoId(Long id) {
        return thiSinhRepository.findById(id);
    }

    public long demTongSo() {
        return thiSinhRepository.count();
    }

    public long demTrungTuyen() {
        return thiSinhRepository.findByKetQua("Trúng tuyển").size();
    }
}
