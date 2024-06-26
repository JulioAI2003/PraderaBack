package com.pradera.praderaback.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class IngresoDTO {

    //LISTAR
    private Long id;
    private String productoNombre;
    private int cantidad;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime fecha;

    //GUARDAR
    private Long productoId;
}
