package org.example.tracuu.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "xt_nganh")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nganh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idnganh;

    @Column(nullable = false, unique = true)
    private String manganh;

    @Column(nullable = false)
    private String tennganh;

    @Column(name = "n_tohopgoc")
    private String tohopGoc;

    @Column(name = "n_chitieu")
    private Integer chiTieu;

    @Column(name = "n_diemsan")
    private BigDecimal diemSan;

    @Column(name = "n_diemtrungtuyen")
    private BigDecimal diemTrungTuyen;

    @Column(name = "n_tuyenthang")
    private String tuyenThang;

    @Column(name = "n_dgnl")
    private String dgnl;

    @Column(name = "n_thpt")
    private String thpt;

    @Column(name = "n_vsat")
    private String vsat;
}
