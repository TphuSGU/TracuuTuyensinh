package org.example.tracuu.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Test các API endpoints công khai
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ApiEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllMajors() throws Exception {
        // Test: Lấy danh sách tất cả các ngành
        mockMvc.perform(get("/api/majors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void testGetMajorByCode() throws Exception {
        // Test: Lấy thông tin ngành theo mã
        mockMvc.perform(get("/api/majors/7140201"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.manganh").value("7140201"))
                .andExpect(jsonPath("$.tennganh").value("Giáo dục Mầm non"));
    }

    @Test
    public void testGetMajorByCodeNotFound() throws Exception {
        // Test: Lấy ngành không tồn tại
        mockMvc.perform(get("/api/majors/NOTEXIST"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetCombinationsByMajor() throws Exception {
        // Test: Lấy tổ hợp môn của ngành
        mockMvc.perform(get("/api/combinations/7140201"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void testGetConversionRulesVSAT() throws Exception {
        // Test: Lấy bảng quy đổi VSAT
        mockMvc.perform(get("/api/conversion-rules/VSAT"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void testGetConversionRulesDGNL() throws Exception {
        // Test: Lấy bảng quy đổi DGNL
        mockMvc.perform(get("/api/conversion-rules/DGNL"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void testGetSubjectConversionRules() throws Exception {
        // Test: Lấy quy đổi điểm theo môn
        mockMvc.perform(get("/api/conversion-rules/VSAT/TO"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void testPublicPagesAccessible() throws Exception {
        // Test: Các trang công khai có thể truy cập
        mockMvc.perform(get("/gioi-thieu"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/thong-tin-tuyen-sinh"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/tinh-diem-dgnl"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/tinh-diem-vsat"))
                .andExpect(status().isOk());
    }
}
