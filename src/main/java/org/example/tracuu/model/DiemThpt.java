package org.example.tracuu.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_diemthixettuyen")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiemThpt {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddiemthi", nullable = false)
    private Integer iddiemthi;

    @Column(name = "cccd", nullable = false, length = 20, unique = true)
    private String cccd;

    @Column(name = "sobaodanh", length = 45)
    private String sobaodanh;

    @Column(name = "d_phuongthuc", length = 10)
    private String dPhuongthuc;

    @Column(name = "TO", precision = 8, scale = 2)
    private java.math.BigDecimal to;

    @Column(name = "LI", precision = 8, scale = 2)
    private java.math.BigDecimal li;

    @Column(name = "HO", precision = 8, scale = 2)
    private java.math.BigDecimal ho;

    @Column(name = "SI", precision = 8, scale = 2)
    private java.math.BigDecimal si;

    @Column(name = "SU", precision = 8, scale = 2)
    private java.math.BigDecimal su;

    @Column(name = "DI", precision = 8, scale = 2)
    private java.math.BigDecimal di;

    @Column(name = "VA", precision = 8, scale = 2)
    private java.math.BigDecimal va;

    @Column(name = "GDCD", precision = 8, scale = 2)
    private java.math.BigDecimal gdcd;

    @Column(name = "N1_THI", precision = 8, scale = 2)
    private java.math.BigDecimal n1Thi;

    @Column(name = "N1_CC", precision = 8, scale = 2)
    private java.math.BigDecimal n1Cc;

    @Column(name = "CNCN", precision = 8, scale = 2)
    private java.math.BigDecimal cncn;

    @Column(name = "CNNN", precision = 8, scale = 2)
    private java.math.BigDecimal cnnn;

    @Column(name = "TI", precision = 8, scale = 2)
    private java.math.BigDecimal ti;

    @Column(name = "KTPL", precision = 8, scale = 2)
    private java.math.BigDecimal ktpl;

    @Column(name = "NL1", precision = 8, scale = 2)
    private java.math.BigDecimal nl1;

    @Column(name = "NK1", precision = 8, scale = 2)
    private java.math.BigDecimal nk1;

    @Column(name = "NK2", precision = 8, scale = 2)
    private java.math.BigDecimal nk2;

    @Column(name = "NK3", precision = 8, scale = 2)
    private java.math.BigDecimal nk3;

    @Column(name = "NK4", precision = 8, scale = 2)
    private java.math.BigDecimal nk4;

    @Column(name = "NK5", precision = 8, scale = 2)
    private java.math.BigDecimal nk5;

    @Column(name = "NK6", precision = 8, scale = 2)
    private java.math.BigDecimal nk6;
}
