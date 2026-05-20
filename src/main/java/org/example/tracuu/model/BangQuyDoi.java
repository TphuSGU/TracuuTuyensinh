package org.example.tracuu.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "xt_bangquydoi")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BangQuyDoi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idqd", nullable = false)
    private Integer idqd;

    @Column(name = "d_phuongthuc", length = 255)
    private String phuongThuc;

    @Column(name = "d_tohop", length = 255)
    private String toHop;

    @Column(name = "d_mon", length = 255)
    private String mon;

    @Column(name = "d_diema", precision = 38, scale = 2)
    private BigDecimal diemA;

    @Column(name = "d_diemb", precision = 38, scale = 2)
    private BigDecimal diemB;

    @Column(name = "d_diemc", precision = 38, scale = 2)
    private BigDecimal diemC;

    @Column(name = "d_diemd", precision = 38, scale = 2)
    private BigDecimal diemD;

    @Column(name = "d_maquydoi", length = 255)
    private String maQuyDoi;

    @Column(name = "d_phanvi", length = 255)
    private String phanVi;
}
