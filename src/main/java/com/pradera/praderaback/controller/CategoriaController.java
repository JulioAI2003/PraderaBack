package com.pradera.praderaback.controller;

import com.pradera.praderaback.dto.CategoriaDTO;
import com.pradera.praderaback.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

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
