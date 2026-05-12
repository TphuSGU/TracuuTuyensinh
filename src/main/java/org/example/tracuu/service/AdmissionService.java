package org.example.tracuu.service;

import org.example.tracuu.model.*;
import org.example.tracuu.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdmissionService {

    private final NganhRepository nganhRepository;
    private final NganhTohopRepository nganhTohopRepository;
    private final NguyenVongRepository nguyenVongRepository;
    private final BangQuyDoiRepository bangQuyDoiRepository;
    private final TohopMonthiRepository tohopMonthiRepository;

    public AdmissionService(NganhRepository nganhRepository,
                            NganhTohopRepository nganhTohopRepository,
                            NguyenVongRepository nguyenVongRepository,
                            BangQuyDoiRepository bangQuyDoiRepository,
                            TohopMonthiRepository tohopMonthiRepository) {
        this.nganhRepository = nganhRepository;
        this.nganhTohopRepository = nganhTohopRepository;
        this.nguyenVongRepository = nguyenVongRepository;
        this.bangQuyDoiRepository = bangQuyDoiRepository;
        this.tohopMonthiRepository = tohopMonthiRepository;
    }

    // Nganh
    public List<Nganh> layTatCaNganh() {
        return nganhRepository.findAll();
    }

    public Optional<Nganh> timNganhTheoMa(String manganh) {
        return nganhRepository.findByManganh(manganh);
    }

    // NganhTohop
    public List<NganhTohop> layTohopTheoNganh(String manganh) {
        return nganhTohopRepository.findByManganh(manganh);
    }

    public List<NganhTohop> layTatCaTohop() {
        return nganhTohopRepository.findAll();
    }

    // NguyenVong
    public List<NguyenVong> layNguyenVongTheoCccd(String cccd) {
        return nguyenVongRepository.findByCccdOrderByThuTuAsc(cccd);
    }

    // BangQuyDoi
    public List<BangQuyDoi> layBangQuyDoiTheoPhuongThuc(String phuongThuc) {
        return bangQuyDoiRepository.findByPhuongThuc(phuongThuc);
    }

    public List<BangQuyDoi> layQuyDoiTheoMon(String phuongThuc, String mon) {
        return bangQuyDoiRepository.findByPhuongThucAndMon(phuongThuc, mon);
    }

    // TohopMonthi
    public Optional<TohopMonthi> timChiTietTohop(String matohop) {
        return tohopMonthiRepository.findByMatohop(matohop);
    }
}
