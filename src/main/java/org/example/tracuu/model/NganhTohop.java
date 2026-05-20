package org.example.tracuu.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "xt_nganh_tohop")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NganhTohop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "manganh", nullable = false, length = 255)
    private String manganh;

    @Column(name = "matohop", nullable = false, length = 255)
    private String matohop;

    @Column(name = "th_mon1", length = 255)
    private String mon1;
    @Column(name = "hsmon1")
    private Integer hsmon1;

    @Column(name = "th_mon2", length = 255)
    private String mon2;
    @Column(name = "hsmon2")
    private Integer hsmon2;

    @Column(name = "th_mon3", length = 255)
    private String mon3;
    @Column(name = "hsmon3")
    private Integer hsmon3;

    @Column(name = "tb_keys", length = 255)
    private String tbKeys;

    @Column(name = "N1")
    private Boolean n1;

    @Column(name = "TO")
    private Boolean to;

    @Column(name = "LI")
    private Boolean li;

    @Column(name = "HO")
    private Boolean ho;

    @Column(name = "SI")
    private Boolean si;

    @Column(name = "VA")
    private Boolean va;

    @Column(name = "SU")
    private Boolean su;

    @Column(name = "DI")
    private Boolean di;

    @Column(name = "TI")
    private Boolean ti;

    @Column(name = "KHAC")
    private Boolean khac;

    @Column(name = "KTPL")
    private Boolean ktpl;

    @Column(name = "dolech", precision = 38, scale = 2)
    private BigDecimal dolech;
}
