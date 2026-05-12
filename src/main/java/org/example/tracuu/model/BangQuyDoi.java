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
    private Integer idqd;

    @Column(name = "d_phuongthuc")
    private String phuongThuc;

    @Column(name = "d_tohop")
    private String toHop;

    @Column(name = "d_mon")
    private String mon;

    @Column(name = "d_diema")
    private BigDecimal diemA;

    @Column(name = "d_diemb")
    private BigDecimal diemB;

    @Column(name = "d_diemc")
    private BigDecimal diemC;

    @Column(name = "d_diemd")
    private BigDecimal diemD;

    @Column(name = "d_maquydoi")
    private String maQuyDoi;

    @Column(name = "d_phanvi")
    private String phanVi;
}
