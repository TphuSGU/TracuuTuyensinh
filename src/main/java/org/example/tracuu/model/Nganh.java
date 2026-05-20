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
    @Column(name = "idnganh", nullable = false)
    private Integer idnganh;

    @Column(name = "manganh", nullable = false, length = 255)
    private String manganh;

    @Column(name = "tennganh", nullable = false, length = 255)
    private String tennganh;

    @Column(name = "n_tohopgoc", length = 255)
    private String tohopGoc;

    @Column(name = "n_chitieu", nullable = false)
    private Integer chiTieu;

    @Column(name = "n_diemsan", precision = 38, scale = 2)
    private BigDecimal diemSan;

    @Column(name = "n_diemtrungtuyen", precision = 38, scale = 2)
    private BigDecimal diemTrungTuyen;

    @Column(name = "n_tuyenthang", length = 255)
    private String tuyenThang;

    @Column(name = "n_dgnl", length = 255)
    private String dgnl;

    @Column(name = "n_thpt", length = 255)
    private String thpt;

    @Column(name = "n_vsat", length = 255)
    private String vsat;

    @Column(name = "sl_xtt")
    private Integer slXtt;

    @Column(name = "sl_dgnl")
    private Integer slDgnl;

    @Column(name = "sl_vsat")
    private Integer slVsat;

    @Column(name = "sl_thpt", length = 45)
    private String slThpt;
}
