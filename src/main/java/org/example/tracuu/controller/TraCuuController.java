package org.example.tracuu.controller;

import org.example.tracuu.model.ThiSinh;
import org.example.tracuu.model.NguyenVong;
import org.example.tracuu.model.Nganh;
import org.example.tracuu.model.BangQuyDoi;
import org.example.tracuu.service.ThiSinhService;
import org.example.tracuu.service.AdmissionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
public class TraCuuController {

    private final ThiSinhService thiSinhService;
    private final AdmissionService admissionService;

    public TraCuuController(ThiSinhService thiSinhService,
            AdmissionService admissionService) {
        this.thiSinhService = thiSinhService;
        this.admissionService = admissionService;
    }

    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication == null || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }
        return "redirect:/tracuu";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "redirect:/tracuu";
    }

    @GetMapping("/gioi-thieu")
    public String gioiThieu() {
        return "gioithieu";
    }

    @GetMapping("/thong-tin-tuyen-sinh")
    public String thongTinTuyenSinh() {
        return "thongtintuyensinh";
    }

    @GetMapping("/tinh-diem-dgnl")
    public String tinhDiemDgnl(Model model) {
        System.out.println("=== TINH DIEM DGNL CONTROLLER ===");

        try {
            // Lấy tất cả ngành
            List<Nganh> tatCaNganh = admissionService.layTatCaNganh();
            System.out.println("Total majors from DB: " + tatCaNganh.size());

            // Lấy danh sách ngành hỗ trợ ĐGNL

            List<Nganh> danhSachNganh = tatCaNganh.stream()
                    .filter(n -> {
                        String dgnl = n.getDgnl();
                        if (dgnl == null)
                            return false;
                    return "Y".equalsIgnoreCase(dgnl.trim());
                    })
                    .collect(Collectors.toList());

            // System.out.println("DGNL majors filtered: " + danhSachNganh.size());

            // if (!danhSachNganh.isEmpty()) {
            // System.out.println("First major: " + danhSachNganh.get(0).getManganh() + " -
            // " + danhSachNganh.get(0).getTennganh());
            // } else {
            // System.out.println("WARNING: No DGNL majors found!");
            // // Nếu không có ngành DGNL, lấy tất cả ngành để test
            // danhSachNganh = tatCaNganh;
            // System.out.println("Using all majors for testing: " + danhSachNganh.size());
            // }

            model.addAttribute("danhSachNganh", danhSachNganh);
            model.addAttribute("totalMajors", tatCaNganh.size());

            // Lấy bảng quy đổi ĐGNL
            List<BangQuyDoi> bangQuyDoiDGNL = admissionService.layBangQuyDoiTheoPhuongThuc("DGNL");
            System.out.println("DGNL conversion rules: " + bangQuyDoiDGNL.size());
            model.addAttribute("bangQuyDoiDGNL", bangQuyDoiDGNL);

        } catch (Exception e) {
            System.err.println("ERROR in tinhDiemDgnl: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("danhSachNganh", new ArrayList<>());
            model.addAttribute("bangQuyDoiDGNL", new ArrayList<>());
            model.addAttribute("errorMessage", "Lỗi khi tải dữ liệu: " + e.getMessage());
        }

        return "tinh-diem-dgnl";
    }

    @GetMapping("/tinh-diem-vsat")
    public String tinhDiemVsat(Model model) {
        try {
            // Lấy tất cả ngành
            List<Nganh> tatCaNganh = admissionService.layTatCaNganh();

            // Lấy danh sách ngành hỗ trợ VSAT
            List<Nganh> danhSachNganhVsat = tatCaNganh.stream()
                    .filter(n -> n.getVsat() != null && "Y".equalsIgnoreCase(n.getVsat().trim()))
                    .collect(Collectors.toList());
            if (danhSachNganhVsat.isEmpty()) {
                danhSachNganhVsat = tatCaNganh;
            }

            // Lấy danh sách ngành hỗ trợ THPT (lấy tất cả ngành như yêu cầu)
            List<Nganh> danhSachNganhThpt = tatCaNganh;

            model.addAttribute("danhSachNganh", danhSachNganhVsat);
            model.addAttribute("danhSachNganhVsat", danhSachNganhVsat);
            model.addAttribute("danhSachNganhThpt", danhSachNganhThpt);
            model.addAttribute("totalMajors", tatCaNganh.size());

            // Lấy bảng quy đổi VSAT
            List<BangQuyDoi> bangQuyDoiVSAT = admissionService.layBangQuyDoiTheoPhuongThuc("VSAT");
            model.addAttribute("bangQuyDoiVSAT", bangQuyDoiVSAT);

        } catch (Exception e) {
            System.err.println("ERROR in tinhDiemVsat: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("danhSachNganh", new ArrayList<>());
            model.addAttribute("bangQuyDoiVSAT", new ArrayList<>());
            model.addAttribute("errorMessage", "Lỗi khi tải dữ liệu: " + e.getMessage());
        }

        return "tinh-diem-vsat";
    }

    @GetMapping("/tracuu")
    public String traCuu(Authentication authentication,
            @RequestParam(value = "loginSuccess", required = false) String loginSuccess,
            Model model) {
        if (authentication == null || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }

        if (loginSuccess != null) {
            model.addAttribute("successMessage", "Đăng nhập thành công! Chào mừng bạn quay trở lại.");
        }

        String cccd = authentication.getName();
        System.out.println(">>> TraCuu: CCCD = " + cccd);
        model.addAttribute("sbd", cccd);

        try {
            Optional<ThiSinh> thiSinhOpt = thiSinhService.timTheoCccd(cccd);
            System.out.println(">>> TraCuu: ThiSinh found = " + thiSinhOpt.isPresent());

            if (thiSinhOpt.isPresent()) {
                ThiSinh thiSinh = thiSinhOpt.get();
                model.addAttribute("thiSinh", thiSinh);
                model.addAttribute("hoTen", thiSinh.getHo() + " " + thiSinh.getTen());
                System.out.println(">>> TraCuu: Ho ten = " + thiSinh.getHo() + " " + thiSinh.getTen());

                // Lấy danh sách nguyện vọng từ AdmissionService
                List<NguyenVong> danhSachNguyenVong = admissionService.layNguyenVongTheoCccd(cccd);
                System.out.println(">>> TraCuu: So nguyen vong = " + danhSachNguyenVong.size());
                model.addAttribute("danhSachNguyenVong", danhSachNguyenVong);
                // Fetch map of tenNganh and diemChuan for all NV
                Map<String, String> tenNganhMap = new HashMap<>();
                Map<String, java.math.BigDecimal> diemChuanMap = new HashMap<>();
                for (NguyenVong nv : danhSachNguyenVong) {
                    if (nv.getManganh() != null) {
                        admissionService.timNganhTheoMa(nv.getManganh().trim())
                                .ifPresent(n -> {
                                    tenNganhMap.put(nv.getManganh(), n.getTennganh());
                                    if (n.getDiemTrungTuyen() != null) {
                                        diemChuanMap.put(nv.getManganh(), n.getDiemTrungTuyen());
                                    }
                                });
                    }
                }
                model.addAttribute("tenNganhMap", tenNganhMap);
                model.addAttribute("diemChuanMap", diemChuanMap);

                // Tìm nguyện vọng trúng tuyển (nếu có)
                NguyenVong nvTrungTuyen = danhSachNguyenVong.stream()
                        .filter(nv -> "Trúng tuyển".equalsIgnoreCase(nv.getKetQua()))
                        .findFirst()
                        .orElse(null);

                if (nvTrungTuyen != null) {
                    System.out.println(">>> TraCuu: Trung tuyen nganh = " + nvTrungTuyen.getManganh());
                    model.addAttribute("coTrungTuyen", true);
                    model.addAttribute("nvTrungTuyen", nvTrungTuyen);
                    // Lấy tên ngành trúng tuyển
                    admissionService.timNganhTheoMa(nvTrungTuyen.getManganh().trim()).ifPresent(nganh -> {
                        System.out.println(">>> TraCuu: Ten nganh = " + nganh.getTennganh());
                        model.addAttribute("tenNganhTrungTuyen", nganh.getTennganh());
                        model.addAttribute("diemChuanTrungTuyen", nganh.getDiemTrungTuyen());
                    });
                } else {
                    System.out.println(">>> TraCuu: Khong trung tuyen");
                    model.addAttribute("coTrungTuyen", false);
                    if (!danhSachNguyenVong.isEmpty()) {
                        model.addAttribute("message",
                                "Rất tiếc, bạn chưa trúng tuyển trong đợt xét tuyển này. " +
                                        "Chúc bạn may mắn ở các đợt xét tuyển bổ sung hoặc nguyện vọng khác.");
                    }
                }
            } else {
                System.out.println(">>> TraCuu: Khong tim thay thi sinh");
                model.addAttribute("message",
                        "Không tìm thấy thông tin thí sinh với CCCD: " + cccd + " trong hệ thống.");
            }
        } catch (Exception e) {
            System.err.println(">>> TraCuu ERROR: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("message", "Có lỗi xảy ra khi tải thông tin. Vui lòng thử lại sau.");
        }

        return "tracuu";
    }
}
