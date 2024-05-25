package com.pradera.praderaback.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.type.LocalDateTimeType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="ingr_pra")
public class IngresosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "id_prod_pra",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_ingr_id_prod_pra")
    )
    private ProductoModel producto;

    @Column(name = "cant_ingr_pra", nullable=false)
    private int cantidad;

    @Column(name = "fech_ingr_pra", nullable=false)
    private LocalDateTime fecha = LocalDateTime.now();
}
