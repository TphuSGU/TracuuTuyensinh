package org.example.tracuu.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "xt_thisinhxettuyen25")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThiSinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idthisinh;

    @Column(unique = true, length = 20)
    private String cccd;

    @Column(length = 45)
    private String sobaodanh;

    @Column(length = 100)
    private String ho;

    @Column(length = 100)
    private String ten;

    @Column(name = "ngay_sinh", length = 45)
    private String ngaySinh;

    @Column(name = "dien_thoai", length = 20)
    private String dienThoai;

    @Column(length = 100)
    private String password;

    @Column(name = "gioi_tinh", length = 10)
    private String gioiTinh;

    @Column(length = 100)
    private String email;

    @Column(name = "noi_sinh", length = 45)
    private String noiSinh;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "doi_tuong", length = 45)
    private String doiTuong;

    @Column(name = "khu_vuc", length = 45)
    private String khuVuc;

    @Column(name = "diem_cong")
    private Double diemCong;

    @Column(name = "diem_uu_tien")
    private Double diemUuTien;
}
