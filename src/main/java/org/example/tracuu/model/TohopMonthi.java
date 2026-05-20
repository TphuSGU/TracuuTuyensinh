package org.example.tracuu.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "xt_tohop_monthi")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TohopMonthi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtohop", nullable = false)
    private Integer idtohop;

    @Column(name = "matohop", nullable = false, length = 255)
    private String matohop;

    @Column(name = "mon1", nullable = false, length = 255)
    private String mon1;

    @Column(name = "mon2", nullable = false, length = 255)
    private String mon2;

    @Column(name = "mon3", nullable = false, length = 255)
    private String mon3;

    @Column(name = "tentohop", length = 255)
    private String tentohop;
}
