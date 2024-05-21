package com.pradera.praderaback.controller;

import com.pradera.praderaback.dto.CategoriaDTO;
import com.pradera.praderaback.dto.ProductoDTO;
import com.pradera.praderaback.dto.ProveedorDTO;
import com.pradera.praderaback.service.CategoriaService;
import com.pradera.praderaback.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/producto")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/bandeja")
    public ResponseEntity<Object> bandeja(
            ProductoDTO dto,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size){
        try {
            return ResponseEntity.ok().body(service.bandeja(dto,page,size));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }


    @RequestMapping(path = "/listar", method = RequestMethod.GET)
    public ResponseEntity<List<ProductoDTO>> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @RequestMapping(path = "/obtener/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductoDTO> obtener(@PathVariable Long id) {
        ProductoDTO dto = service.obtener(id);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/guardar", method = RequestMethod.POST)
    public ResponseEntity<Void> guardar(@RequestBody ProductoDTO dto) {
        if(dto.getId()!=null){
            ProductoDTO productoDTO = service.obtener(dto.getId());
            if(productoDTO == null){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        CategoriaDTO categoriaDTO = categoriaService.obtener(dto.getCategoria().getId());
        if(categoriaDTO == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        service.guardar(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/eliminar/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.obtener(id) != null) {
            service.eliminar(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
