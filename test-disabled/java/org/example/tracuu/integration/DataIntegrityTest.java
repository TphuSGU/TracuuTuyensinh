package org.example.tracuu.integration;

import org.example.tracuu.model.*;
import org.example.tracuu.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test kiểm tra tính toàn vẹn dữ liệu
 */
@SpringBootTest
public class DataIntegrityTest {

    @Autowired
    private ThiSinhRepository thiSinhRepository;

    @Autowired
    private NguyenVongRepository nguyenVongRepository;

    @Autowired
    private NganhRepository nganhRepository;

    @Autowired
    private NganhTohopRepository nganhTohopRepository;

    @Autowired
    private TohopMonthiRepository tohopMonthiRepository;

    @Autowired
    private BangQuyDoiRepository bangQuyDoiRepository;

    @Test
    public void testAllTablesHaveData() {
        // Test: Tất cả các bảng đều có dữ liệu
        assertTrue(thiSinhRepository.count() > 0, "Bang thi sinh phai co du lieu");
        assertTrue(nguyenVongRepository.count() > 0, "Bang nguyen vong phai co du lieu");
        assertTrue(nganhRepository.count() > 0, "Bang nganh phai co du lieu");
        assertTrue(nganhTohopRepository.count() > 0, "Bang nganh-tohop phai co du lieu");
        assertTrue(tohopMonthiRepository.count() > 0, "Bang tohop monthi phai co du lieu");
        assertTrue(bangQuyDoiRepository.count() > 0, "Bang quy doi phai co du lieu");
    }

    @Test
    public void testThiSinhHasNguyenVong() {
        // Test: Thí sinh có nguyện vọng
        ThiSinh thiSinh = thiSinhRepository.findByCccd("TS2025001").orElse(null);
        assertNotNull(thiSinh, "Thi sinh TS2025001 phai ton tai");

        List<NguyenVong> nguyenVongs = nguyenVongRepository.findByCccdOrderByThuTuAsc("TS2025001");
        assertFalse(nguyenVongs.isEmpty(), "Thi sinh TS2025001 phai co nguyen vong");
        assertTrue(nguyenVongs.size() >= 1, "Thi sinh phai co it nhat 1 nguyen vong");
    }

    @Test
    public void testNguyenVongReferencesValidNganh() {
        // Test: Nguyện vọng tham chiếu đến ngành hợp lệ
        List<NguyenVong> nguyenVongs = nguyenVongRepository.findByCccdOrderByThuTuAsc("TS2025001");
        
        for (NguyenVong nv : nguyenVongs) {
            Nganh nganh = nganhRepository.findByManganh(nv.getManganh()).orElse(null);
            assertNotNull(nganh, "Nguyen vong phai tham chieu den nganh hop le: " + nv.getManganh());
        }
    }

    @Test
    public void testNganhHasTohop() {
        // Test: Ngành có tổ hợp môn
        Nganh nganh = nganhRepository.findByManganh("7140201").orElse(null);
        assertNotNull(nganh, "Nganh 7140201 phai ton tai");

        List<NganhTohop> tohops = nganhTohopRepository.findByManganh("7140201");
        assertFalse(tohops.isEmpty(), "Nganh 7140201 phai co to hop mon");
    }

    @Test
    public void testTohopMonthiExists() {
        // Test: Tổ hợp môn thi tồn tại
        assertTrue(tohopMonthiRepository.findByMatohop("A00").isPresent(), "To hop A00 phai ton tai");
        assertTrue(tohopMonthiRepository.findByMatohop("D01").isPresent(), "To hop D01 phai ton tai");
        assertTrue(tohopMonthiRepository.findByMatohop("C00").isPresent(), "To hop C00 phai ton tai");
    }

    @Test
    public void testBangQuyDoiForDGNL() {
        // Test: Bảng quy đổi cho DGNL tồn tại
        List<BangQuyDoi> dgnlRules = bangQuyDoiRepository.findByPhuongThuc("DGNL");
        assertFalse(dgnlRules.isEmpty(), "Phai co quy doi cho DGNL");
    }

    @Test
    public void testBangQuyDoiForVSAT() {
        // Test: Bảng quy đổi cho VSAT tồn tại
        List<BangQuyDoi> vsatRules = bangQuyDoiRepository.findByPhuongThuc("VSAT");
        assertFalse(vsatRules.isEmpty(), "Phai co quy doi cho VSAT");
    }

    @Test
    public void testPasswordFormat() {
        // Test: Format password đúng
        ThiSinh thiSinh = thiSinhRepository.findByCccd("TS2025001").orElse(null);
        assertNotNull(thiSinh);
        assertTrue(thiSinh.getPassword().startsWith("{noop}"), 
                "Password phai bat dau bang {noop}");
    }

    @Test
    public void testNguyenVongHasKetQua() {
        // Test: Nguyện vọng có kết quả
        List<NguyenVong> nguyenVongs = nguyenVongRepository.findByCccdOrderByThuTuAsc("TS2025001");
        
        for (NguyenVong nv : nguyenVongs) {
            assertNotNull(nv.getKetQua(), "Nguyen vong phai co ket qua");
            assertNotNull(nv.getDiemXetTuyen(), "Nguyen vong phai co diem xet tuyen");
        }
    }

    @Test
    public void testAtLeastOneStudentPassedAdmission() {
        // Test: Có ít nhất 1 thí sinh trúng tuyển
        List<NguyenVong> passedStudents = nguyenVongRepository.findAll().stream()
                .filter(nv -> "Trúng tuyển".equalsIgnoreCase(nv.getKetQua()))
                .toList();
        
        assertFalse(passedStudents.isEmpty(), "Phai co it nhat 1 thi sinh trung tuyen");
    }
}
