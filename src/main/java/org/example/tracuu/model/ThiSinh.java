package org.example.tracuu.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "thi_sinh")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThiSinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String soBaoDanh;

    @Column(nullable = false)
    private String hoTen;

    private String ngaySinh;

    private String gioiTinh;

    @Column(nullable = false)
    private String nganhXetTuyen;

    private String maNganh;

    private Double diemMon1;
    private Double diemMon2;
    private Double diemMon3;
    private Double diemUuTien;
    private Double tongDiem;

    @Column(nullable = false)
    private String ketQua; // "Trúng tuyển" hoặc "Không trúng tuyển"

    private String ghiChu;
}
