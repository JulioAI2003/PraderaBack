package com.pradera.praderaback.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KardexResponse {
    private String producto;
    private Integer entradas;
    private Integer salidas;
    private Integer stock;
}
