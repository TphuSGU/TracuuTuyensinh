package org.example.tracuu.controller;

import org.example.tracuu.model.BangQuyDoi;
import org.example.tracuu.model.Nganh;
import org.example.tracuu.model.NganhTohop;
import org.example.tracuu.service.AdmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DataApiController {

    private final AdmissionService admissionService;

    public DataApiController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    /**
     * Lấy danh sách tất cả các ngành
     */
    @GetMapping("/majors")
    public ResponseEntity<List<Nganh>> getAllMajors() {
        return ResponseEntity.ok(admissionService.layTatCaNganh());
    }

    /**
     * Lấy thông tin chi tiết một ngành
     */
    @GetMapping("/majors/{manganh}")
    public ResponseEntity<Nganh> getMajorByCode(@PathVariable String manganh) {
        return admissionService.timNganhTheoMa(manganh)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lấy danh sách tổ hợp môn của một ngành
     */
    @GetMapping("/combinations/{manganh}")
    public ResponseEntity<List<NganhTohop>> getCombinationsByMajor(@PathVariable String manganh) {
        return ResponseEntity.ok(admissionService.layTohopTheoNganh(manganh));
    }

    /**
     * Lấy bảng quy đổi điểm theo phương thức (VSAT, DGNL)
     */
    @GetMapping("/conversion-rules/{method}")
    public ResponseEntity<List<BangQuyDoi>> getConversionRules(@PathVariable String method) {
        return ResponseEntity.ok(admissionService.layBangQuyDoiTheoPhuongThuc(method.toUpperCase()));
    }

    /**
     * Lấy quy đổi điểm cho một môn cụ thể
     */
    @GetMapping("/conversion-rules/{method}/{subject}")
    public ResponseEntity<List<BangQuyDoi>> getSubjectRules(@PathVariable String method, @PathVariable String subject) {
        return ResponseEntity.ok(admissionService.layQuyDoiTheoMon(method.toUpperCase(), subject.toUpperCase()));
    }
}
