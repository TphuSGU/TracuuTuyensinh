package org.example.tracuu.config;

import org.example.tracuu.model.ThiSinh;
import org.example.tracuu.model.User;
import org.example.tracuu.repository.ThiSinhRepository;
import org.example.tracuu.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(ThiSinhRepository thiSinhRepository,
                               UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            // Tạo tài khoản mặc định
            if (userRepository.findByUsername("admin").isEmpty()) {
                userRepository.save(User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .role("ROLE_ADMIN")
                        .hoTen("Quản trị viên")
                        .build());
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                userRepository.save(User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("user123"))
                        .role("ROLE_USER")
                        .hoTen("Người dùng")
                        .build());
            }

                        // Tài khoản thí sinh để đăng nhập xem kết quả trực tiếp theo SBD
                        if (userRepository.findByUsername("TS2025001").isEmpty()) {
                                userRepository.save(User.builder()
                                                .username("TS2025001")
                                                .password(passwordEncoder.encode("123456"))
                                                .role("ROLE_USER")
                                                .hoTen("Nguyễn Văn An")
                                                .build());
                        }

                        if (userRepository.findByUsername("TS2025004").isEmpty()) {
                                userRepository.save(User.builder()
                                                .username("TS2025004")
                                                .password(passwordEncoder.encode("123456"))
                                                .role("ROLE_USER")
                                                .hoTen("Phạm Minh Đức")
                                                .build());
                        }

            // Tạo dữ liệu thí sinh mẫu
            if (thiSinhRepository.count() == 0) {
                thiSinhRepository.save(ThiSinh.builder()
                        .soBaoDanh("TS2025001")
                        .hoTen("Nguyễn Văn An")
                        .ngaySinh("15/03/2007")
                        .gioiTinh("Nam")
                        .nganhXetTuyen("Công nghệ thông tin")
                        .maNganh("7480201")
                        .diemMon1(8.5)
                        .diemMon2(7.0)
                        .diemMon3(9.0)
                        .diemUuTien(0.5)
                        .tongDiem(25.0)
                        .ketQua("Trúng tuyển")
                        .ghiChu("Nguyện vọng 1")
                        .build());

                thiSinhRepository.save(ThiSinh.builder()
                        .soBaoDanh("TS2025002")
                        .hoTen("Trần Thị Bình")
                        .ngaySinh("22/07/2007")
                        .gioiTinh("Nữ")
                        .nganhXetTuyen("Quản trị kinh doanh")
                        .maNganh("7340101")
                        .diemMon1(7.5)
                        .diemMon2(8.0)
                        .diemMon3(6.5)
                        .diemUuTien(1.0)
                        .tongDiem(23.0)
                        .ketQua("Trúng tuyển")
                        .ghiChu("Nguyện vọng 1")
                        .build());

                thiSinhRepository.save(ThiSinh.builder()
                        .soBaoDanh("TS2025003")
                        .hoTen("Lê Hoàng Cường")
                        .ngaySinh("10/01/2007")
                        .gioiTinh("Nam")
                        .nganhXetTuyen("Kỹ thuật phần mềm")
                        .maNganh("7480103")
                        .diemMon1(9.0)
                        .diemMon2(8.5)
                        .diemMon3(9.5)
                        .diemUuTien(0.0)
                        .tongDiem(27.0)
                        .ketQua("Trúng tuyển")
                        .ghiChu("Nguyện vọng 1 - Thủ khoa")
                        .build());

                thiSinhRepository.save(ThiSinh.builder()
                        .soBaoDanh("TS2025004")
                        .hoTen("Phạm Minh Đức")
                        .ngaySinh("05/09/2007")
                        .gioiTinh("Nam")
                        .nganhXetTuyen("Công nghệ thông tin")
                        .maNganh("7480201")
                        .diemMon1(5.0)
                        .diemMon2(4.5)
                        .diemMon3(6.0)
                        .diemUuTien(0.0)
                        .tongDiem(15.5)
                        .ketQua("Không trúng tuyển")
                        .ghiChu("Không đạt điểm chuẩn")
                        .build());

                thiSinhRepository.save(ThiSinh.builder()
                        .soBaoDanh("TS2025005")
                        .hoTen("Hoàng Thị Ema")
                        .ngaySinh("18/11/2007")
                        .gioiTinh("Nữ")
                        .nganhXetTuyen("Ngôn ngữ Anh")
                        .maNganh("7220201")
                        .diemMon1(8.0)
                        .diemMon2(9.0)
                        .diemMon3(7.5)
                        .diemUuTien(0.5)
                        .tongDiem(25.0)
                        .ketQua("Trúng tuyển")
                        .ghiChu("Nguyện vọng 1")
                        .build());

                thiSinhRepository.save(ThiSinh.builder()
                        .soBaoDanh("TS2025006")
                        .hoTen("Võ Thanh Phong")
                        .ngaySinh("28/04/2007")
                        .gioiTinh("Nam")
                        .nganhXetTuyen("Kỹ thuật điện tử")
                        .maNganh("7520203")
                        .diemMon1(7.0)
                        .diemMon2(6.5)
                        .diemMon3(7.0)
                        .diemUuTien(0.0)
                        .tongDiem(20.5)
                        .ketQua("Trúng tuyển")
                        .ghiChu("Nguyện vọng 2")
                        .build());

                thiSinhRepository.save(ThiSinh.builder()
                        .soBaoDanh("TS2025007")
                        .hoTen("Đặng Thị Giang")
                        .ngaySinh("14/06/2007")
                        .gioiTinh("Nữ")
                        .nganhXetTuyen("Kế toán")
                        .maNganh("7340301")
                        .diemMon1(6.0)
                        .diemMon2(5.5)
                        .diemMon3(5.0)
                        .diemUuTien(1.5)
                        .tongDiem(18.0)
                        .ketQua("Không trúng tuyển")
                        .ghiChu("Không đạt điểm chuẩn")
                        .build());

                thiSinhRepository.save(ThiSinh.builder()
                        .soBaoDanh("TS2025008")
                        .hoTen("Bùi Quang Huy")
                        .ngaySinh("30/12/2007")
                        .gioiTinh("Nam")
                        .nganhXetTuyen("Công nghệ thông tin")
                        .maNganh("7480201")
                        .diemMon1(8.0)
                        .diemMon2(7.5)
                        .diemMon3(8.0)
                        .diemUuTien(0.5)
                        .tongDiem(24.0)
                        .ketQua("Trúng tuyển")
                        .ghiChu("Nguyện vọng 1")
                        .build());

                thiSinhRepository.save(ThiSinh.builder()
                        .soBaoDanh("TS2025009")
                        .hoTen("Nguyễn Thị Kim")
                        .ngaySinh("02/08/2007")
                        .gioiTinh("Nữ")
                        .nganhXetTuyen("Y khoa")
                        .maNganh("7720101")
                        .diemMon1(9.5)
                        .diemMon2(9.0)
                        .diemMon3(9.5)
                        .diemUuTien(0.0)
                        .tongDiem(28.0)
                        .ketQua("Trúng tuyển")
                        .ghiChu("Nguyện vọng 1")
                        .build());

                thiSinhRepository.save(ThiSinh.builder()
                        .soBaoDanh("TS2025010")
                        .hoTen("Trần Văn Long")
                        .ngaySinh("20/02/2007")
                        .gioiTinh("Nam")
                        .nganhXetTuyen("Luật")
                        .maNganh("7380101")
                        .diemMon1(6.5)
                        .diemMon2(7.0)
                        .diemMon3(5.5)
                        .diemUuTien(0.0)
                        .tongDiem(19.0)
                        .ketQua("Không trúng tuyển")
                        .ghiChu("Không đạt điểm chuẩn")
                        .build());
            }
        };
    }
}
