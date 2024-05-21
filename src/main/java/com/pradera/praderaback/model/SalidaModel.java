package com.pradera.praderaback.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="sali_pra")
public class SalidaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cant_sali_pra", nullable=false)
    private Integer cantidad;

    @Column(name = "fech_sali_pra", nullable=false)
    private LocalDateTime fecha;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(
            name="id_producto",
            nullable=false,
            foreignKey = @ForeignKey(name="FK_sali_id_prod_pra")
    )
    private ProductoModel producto;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(
            name="id_trabajador",
            nullable=false,
            foreignKey = @ForeignKey(name="FK_sali_id_trab_pra")
    )
    private TrabajadorModel trabajador;
}
