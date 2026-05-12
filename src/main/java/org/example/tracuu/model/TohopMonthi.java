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
    private Integer idtohop;

    @Column(nullable = false, unique = true)
    private String matohop;

    @Column(nullable = false)
    private String mon1;

    @Column(nullable = false)
    private String mon2;

    @Column(nullable = false)
    private String mon3;

    private String tentohop;
}
