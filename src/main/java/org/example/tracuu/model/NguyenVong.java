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
    private Integer idnv;

    @Column(name = "nn_cccd", nullable = false)
    private String cccd;

    @Column(name = "nv_manganh", nullable = false)
    private String manganh;

    @Column(name = "nv_tt", nullable = false)
    private Integer thuTu;

    @Column(name = "diem_thxt")
    private BigDecimal diemThxt;

    @Column(name = "diem_utqd")
    private BigDecimal diemUtqd;

    @Column(name = "diem_xettuyen")
    private BigDecimal diemXetTuyen;

    @Column(name = "nv_ketqua")
    private String ketQua;

    @Column(name = "tt_phuongthuc")
    private String phuongThuc;

    @Column(name = "tt_thm")
    private String toHop;
}
