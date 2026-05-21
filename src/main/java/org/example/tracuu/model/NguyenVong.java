package org.example.tracuu.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "xt_nguyenvongxettuyen")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NguyenVong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnv", nullable = false)
    private Integer idnv;

    @Column(name = "nn_cccd", nullable = false, length = 255)
    private String cccd;

    @Column(name = "nv_manganh", nullable = false, length = 255)
    private String manganh;

    @Column(name = "nv_tt", nullable = false)
    private Integer thuTu;

    @Column(name = "diem_thxt", precision = 10, scale = 5)
    private BigDecimal diemThxt;

    @Column(name = "diem_utqd", precision = 10, scale = 5)
    private BigDecimal diemUtqd;

    @Column(name = "diem_cong", precision = 6, scale = 2)
    private BigDecimal diemCong;

    @Column(name = "diem_xettuyen", precision = 10, scale = 5)
    private BigDecimal diemXetTuyen;

    @Column(name = "nv_ketqua", length = 255)
    private String ketQua;

    @Column(name = "nv_keys", length = 45)
    private String nvKeys;

    @Column(name = "tt_phuongthuc", length = 255)
    private String phuongThuc;

    @Column(name = "tt_thm", length = 255)
    private String toHop;
}
