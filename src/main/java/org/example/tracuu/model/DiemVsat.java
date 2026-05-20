package org.example.tracuu.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "xt_diemvsat")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiemVsat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vsat")
    private Integer id;

    @Column(name = "cccd", length = 20, nullable = false)
    private String cccd;

    @Column(name = "dot_thi", length = 20, nullable = false)
    private String dotThi;

    // Điểm V-SAT từng môn (thang 150)
    @Column(name = "toan_vsat", precision = 5, scale = 2)
    private BigDecimal toanVsat;

    @Column(name = "van_vsat", precision = 5, scale = 2)
    private BigDecimal vanVsat;

    @Column(name = "anh_vsat", precision = 5, scale = 2)
    private BigDecimal anhVsat;

    @Column(name = "ly_vsat", precision = 5, scale = 2)
    private BigDecimal lyVsat;

    @Column(name = "hoa_vsat", precision = 5, scale = 2)
    private BigDecimal hoaVsat;

    @Column(name = "sinh_vsat", precision = 5, scale = 2)
    private BigDecimal sinhVsat;

    @Column(name = "su_vsat", precision = 5, scale = 2)
    private BigDecimal suVsat;

    @Column(name = "dia_vsat", precision = 5, scale = 2)
    private BigDecimal diaVsat;
}
