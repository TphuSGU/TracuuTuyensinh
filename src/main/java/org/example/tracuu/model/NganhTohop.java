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
    private Integer id;

    @Column(nullable = false)
    private String manganh;

    @Column(nullable = false)
    private String matohop;

    @Column(name = "th_mon1")
    private String mon1;
    private Integer hsmon1;

    @Column(name = "th_mon2")
    private String mon2;
    private Integer hsmon2;

    @Column(name = "th_mon3")
    private String mon3;
    private Integer hsmon3;

    @Column(name = "tb_keys", unique = true)
    private String tbKeys;

    private BigDecimal dolech;
}
