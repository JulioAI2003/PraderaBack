package com.pradera.praderaback.controller;

import com.pradera.praderaback.dto.KardexResponse;
import com.pradera.praderaback.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kardex")
public class KardexController {

    @Autowired
    private ProductoService productoService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<KardexResponse>> listar() {
        try{
            List<KardexResponse> data = productoService.kardex();
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
