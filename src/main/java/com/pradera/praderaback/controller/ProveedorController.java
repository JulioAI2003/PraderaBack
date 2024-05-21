package com.pradera.praderaback.controller;

import com.pradera.praderaback.dto.ProveedorDTO;
import com.pradera.praderaback.model.ProveedorModel;
import com.pradera.praderaback.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
    private ProveedorService service;

    @RequestMapping(path = "/listar", method = RequestMethod.GET)
    public ResponseEntity<List<ProveedorDTO>> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @RequestMapping(path = "/obtener/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProveedorDTO> obtener(@PathVariable Long id) {
        ProveedorDTO dto = service.obtener(id);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/guardar", method = RequestMethod.POST)
    public ResponseEntity<Void> guardar(@RequestBody ProveedorDTO dto) {
        if(dto.getId()!=null){
            ProveedorDTO proveedorDTO = service.obtener(dto.getId());
            if(proveedorDTO == null){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
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
