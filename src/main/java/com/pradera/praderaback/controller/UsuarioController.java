package com.pradera.praderaback.controller;

import com.pradera.praderaback.dto.UsuarioDTO;
import com.pradera.praderaback.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @RequestMapping(path = "/listar", method = RequestMethod.GET)
    public ResponseEntity<List<UsuarioDTO>> listar() {
        try {
            return new ResponseEntity<>(service.listar(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/obtener/{id}", method = RequestMethod.GET)
    public ResponseEntity<UsuarioDTO> obtener(@PathVariable Long id) {
        try{
            return new ResponseEntity<>(service.obtener(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/registrar", method = RequestMethod.POST)
    public ResponseEntity<Void> registrar(@RequestBody UsuarioDTO dto) {
        try{
            if(service.existsByUsername(dto.getUsername())){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            service.guardar(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/actualizar", method = RequestMethod.POST)
    public ResponseEntity<Void> actualizar(@RequestBody UsuarioDTO dto) {
        try{
            if(!service.avaibleUsername(dto)){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            service.guardar(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/eliminar/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try{
            service.eliminar(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
