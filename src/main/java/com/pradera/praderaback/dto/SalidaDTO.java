package com.pradera.praderaback.dto;

import com.pradera.praderaback.model.ProductoModel;
import com.pradera.praderaback.model.TrabajadorModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class SalidaDTO {

    private Long id;
    private Integer cantidad;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime fecha;
    private ProductoDTO producto;
    private TrabajadorDTO trabajador;
}
