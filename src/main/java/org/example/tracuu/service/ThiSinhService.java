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

    public Optional<ThiSinh> timTheoCccd(String cccd) {
        return thiSinhRepository.findByCccd(cccd);
    }

    public List<ThiSinh> layTatCa() {
        return thiSinhRepository.findAll();
    }

    public ThiSinh luu(ThiSinh thiSinh) {
        return thiSinhRepository.save(thiSinh);
    }

    public void xoa(Integer id) {
        thiSinhRepository.deleteById(id);
    }

    public Optional<ThiSinh> timTheoId(Integer id) {
        return thiSinhRepository.findById(id);
    }

    public long demTongSo() {
        return thiSinhRepository.count();
    }
}
