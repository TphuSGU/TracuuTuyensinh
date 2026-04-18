package org.example.tracuu.controller;

import org.example.tracuu.model.ThiSinh;
import org.example.tracuu.service.ThiSinhService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class TraCuuController {

    private final ThiSinhService thiSinhService;

    public TraCuuController(ThiSinhService thiSinhService) {
        this.thiSinhService = thiSinhService;
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

    @GetMapping("/tracuu")
    public String traCuu(@RequestParam(required = false) String sbd, Model model) {
        String soBaoDanh = sbd == null ? "" : sbd.trim();
        boolean daTim = !soBaoDanh.isBlank();

        model.addAttribute("sbd", soBaoDanh);
        model.addAttribute("daTim", daTim);

        if (!daTim) {
            return "tracuu";
        }

        Optional<ThiSinh> thiSinh = thiSinhService.timTheoSoBaoDanh(soBaoDanh);
        if (thiSinh.isPresent() && "Trúng tuyển".equalsIgnoreCase(thiSinh.get().getKetQua())) {
            model.addAttribute("thiSinh", thiSinh.get());
            return "tracuu";
        }

        model.addAttribute("message", "Bạn không trúng tuyển vào trường");
        return "tracuu";
    }

    @GetMapping("/ketqua")
    public String ketQuaRedirect(@RequestParam(required = false) String sbd) {
        if (sbd != null && !sbd.isBlank()) {
            return "redirect:/tracuu?sbd=" + sbd.trim();
        }
        return "redirect:/tracuu";
    }
}
