package org.example.tracuu.controller;

import org.example.tracuu.model.NguyenVong;
import org.example.tracuu.model.ThiSinh;
import org.example.tracuu.model.Nganh;
import org.example.tracuu.repository.NguyenVongRepository;
import org.example.tracuu.repository.ThiSinhRepository;
import org.example.tracuu.repository.NganhRepository;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/debug")
public class DebugController {

    private final ThiSinhRepository thiSinhRepository;
    private final NguyenVongRepository nguyenVongRepository;
    private final NganhRepository nganhRepository;

    public DebugController(ThiSinhRepository thiSinhRepository, 
                          NguyenVongRepository nguyenVongRepository,
                          NganhRepository nganhRepository) {
        this.thiSinhRepository = thiSinhRepository;
        this.nguyenVongRepository = nguyenVongRepository;
        this.nganhRepository = nganhRepository;
    }

    @PostMapping("/test-login")
    public Map<String, Object> testLogin(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            HttpServletRequest request) {
        
        Map<String, Object> result = new HashMap<>();
        result.put("username_param", username);
        result.put("password_param", password != null ? "***" : null);
        
        // Get all parameters
        Map<String, String> allParams = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            allParams.put(paramName, request.getParameter(paramName));
        }
        result.put("all_parameters", allParams);
        
        // Get headers
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        result.put("headers", headers);
        
        result.put("content_type", request.getContentType());
        result.put("method", request.getMethod());
        
        System.out.println("=== DEBUG TEST LOGIN ===");
        System.out.println("Username: " + username);
        System.out.println("Password: " + (password != null ? "***" : "null"));
        System.out.println("All params: " + allParams);
        System.out.println("Content-Type: " + request.getContentType());
        
        return result;
    }

    @GetMapping("/check-data/{cccd}")
    public Map<String, Object> checkData(@PathVariable String cccd) {
        Map<String, Object> result = new HashMap<>();
        
        // Kiểm tra thí sinh
        var thiSinhOpt = thiSinhRepository.findByCccd(cccd);
        result.put("thiSinh_found", thiSinhOpt.isPresent());
        if (thiSinhOpt.isPresent()) {
            ThiSinh ts = thiSinhOpt.get();
            result.put("thiSinh_ho", ts.getHo());
            result.put("thiSinh_ten", ts.getTen());
            result.put("thiSinh_ngaySinh", ts.getNgaySinh());
        }
        
        // Kiểm tra nguyện vọng
        List<NguyenVong> nguyenVongs = nguyenVongRepository.findByCccdOrderByThuTuAsc(cccd);
        result.put("nguyen_vong_count", nguyenVongs.size());
        result.put("nguyen_vong_list", nguyenVongs);
        
        // Kiểm tra nguyện vọng trúng tuyển
        NguyenVong nvTrungTuyen = nguyenVongs.stream()
                .filter(nv -> "Trúng tuyển".equalsIgnoreCase(nv.getKetQua()))
                .findFirst()
                .orElse(null);
        
        result.put("co_trung_tuyen", nvTrungTuyen != null);
        if (nvTrungTuyen != null) {
            result.put("nganh_trung_tuyen", nvTrungTuyen.getManganh());
            result.put("diem_trung_tuyen", nvTrungTuyen.getDiemXetTuyen());
        }
        
        System.out.println("=== DEBUG CHECK DATA ===");
        System.out.println("CCCD: " + cccd);
        System.out.println("Thí sinh found: " + thiSinhOpt.isPresent());
        System.out.println("Nguyện vọng count: " + nguyenVongs.size());
        System.out.println("Có trúng tuyển: " + (nvTrungTuyen != null));
        
        return result;
    }

    @GetMapping("/all-students")
    public Map<String, Object> getAllStudents() {
        Map<String, Object> result = new HashMap<>();
        
        List<ThiSinh> allStudents = thiSinhRepository.findAll();
        result.put("total_students", allStudents.size());
        result.put("students", allStudents);
        
        List<NguyenVong> allNguyenVong = nguyenVongRepository.findAll();
        result.put("total_nguyen_vong", allNguyenVong.size());
        
        long trungTuyenCount = allNguyenVong.stream()
                .filter(nv -> "Trúng tuyển".equalsIgnoreCase(nv.getKetQua()))
                .count();
        result.put("trung_tuyen_count", trungTuyenCount);
        
        return result;
    }
    
    @GetMapping("/majors")
    public Map<String, Object> checkMajors() {
        Map<String, Object> result = new HashMap<>();
        
        List<Nganh> allMajors = nganhRepository.findAll();
        result.put("total_majors", allMajors.size());
        result.put("majors", allMajors);
        
        // Chấp nhận cả "x", "X", "Có", "có", "CO", "co"
        long dgnlCount = allMajors.stream()
                .filter(n -> {
                    String dgnl = n.getDgnl();
                    if (dgnl == null) return false;
                    dgnl = dgnl.trim().toLowerCase();
                    return dgnl.equals("x") || dgnl.equals("có") || dgnl.equals("co");
                })
                .count();
        result.put("dgnl_majors_count", dgnlCount);
        
        List<Nganh> dgnlMajors = allMajors.stream()
                .filter(n -> {
                    String dgnl = n.getDgnl();
                    if (dgnl == null) return false;
                    dgnl = dgnl.trim().toLowerCase();
                    return dgnl.equals("x") || dgnl.equals("có") || dgnl.equals("co");
                })
                .toList();
        result.put("dgnl_majors", dgnlMajors);
        
        System.out.println("=== DEBUG MAJORS ===");
        System.out.println("Total majors: " + allMajors.size());
        System.out.println("DGNL majors: " + dgnlCount);
        
        return result;
    }
}
