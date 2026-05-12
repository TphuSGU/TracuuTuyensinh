package org.example.tracuu.integration;

import org.example.tracuu.model.ThiSinh;
import org.example.tracuu.repository.ThiSinhRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test tích hợp cho luồng đăng nhập
 */
@SpringBootTest
@AutoConfigureMockMvc
public class LoginFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ThiSinhRepository thiSinhRepository;

    @Test
    public void testLoginPageAccessible() throws Exception {
        // Test: Trang login có thể truy cập được
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testLoginWithValidCredentials() throws Exception {
        // Test: Đăng nhập với thông tin đúng
        mockMvc.perform(post("/login")
                        .param("username", "123456789")
                        .param("password", "01012007")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tracuu"));
    }

    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        // Test: Đăng nhập với thông tin sai
        mockMvc.perform(post("/login")
                        .param("username", "wronguser")
                        .param("password", "wrongpass")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error=true"));
    }

    @Test
    public void testAccessProtectedPageWithoutLogin() throws Exception {
        // Test: Truy cập trang bảo vệ khi chưa đăng nhập
        mockMvc.perform(get("/tracuu"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "123456789", roles = "USER")
    public void testAccessProtectedPageWithLogin() throws Exception {
        // Test: Truy cập trang bảo vệ sau khi đăng nhập
        mockMvc.perform(get("/tracuu"))
                .andExpect(status().isOk())
                .andExpect(view().name("tracuu"));
    }

    @Test
    public void testThiSinhDataExists() {
        // Test: Dữ liệu thí sinh tồn tại trong database
        ThiSinh thiSinh = thiSinhRepository.findByCccd("123456789").orElse(null);
        
        assertNotNull(thiSinh, "Thi sinh 123456789 phai ton tai");
        assertEquals("123456789", thiSinh.getCccd());
        assertEquals("{noop}01012007", thiSinh.getPassword());
    }

    @Test
    public void testMultipleStudentAccountsExist() {
        // Test: Nhiều tài khoản thí sinh tồn tại
        assertTrue(thiSinhRepository.findByCccd("123456789").isPresent());
        assertTrue(thiSinhRepository.findByCccd("TS2025001").isPresent());
        assertTrue(thiSinhRepository.findByCccd("3123410269").isPresent());
    }
}
