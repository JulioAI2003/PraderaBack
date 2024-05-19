package com.pradera.praderaback.controller;

import com.pradera.praderaback.dto.CategoriaDTO;
import com.pradera.praderaback.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping("/bandeja")
    public ResponseEntity<Object> bandeja(
            CategoriaDTO dto,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size){
        try {
            return ResponseEntity.ok().body(service.bandeja(dto,page,size));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }









    @GetMapping("/listar")
    public @ResponseBody List<CategoriaDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public @ResponseBody CategoriaDTO obtenerID(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @PostMapping("/guardar")
    public @ResponseBody void guardar(@RequestBody CategoriaDTO dto) {
        service.guardar(dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public @ResponseBody void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }

}