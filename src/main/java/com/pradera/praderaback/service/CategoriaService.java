package com.pradera.praderaback.service;

import com.pradera.praderaback.dto.CategoriaDTO;
import com.pradera.praderaback.model.CategoriaModel;
import com.pradera.praderaback.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repositorio;

    public void guardar(CategoriaDTO dto) {
        CategoriaModel categoria = new CategoriaModel();
        categoria.setId(dto.getId());

        if (dto.getId() != null){
            categoria = repositorio.getById(dto.getId());
            categoria.setNombre(dto.getNombre());

        }else{
            categoria.setId(dto.getId());
            categoria.setNombre(dto.getNombre());

        }
        repositorio.save(categoria);
    }

    public void eliminar(Integer id) {
        repositorio.deleteById(id);
    }

    public List<CategoriaDTO> listar() {
        List<CategoriaDTO> listadto = new ArrayList<>();
        List<CategoriaModel> listamodelo= repositorio.findAll();
        for (CategoriaModel modelo:listamodelo
             ) {
            CategoriaDTO dto = new CategoriaDTO();
            dto.setId(modelo.getId());
            dto.setNombre(modelo.getNombre());
            listadto.add(dto);
        }
        return listadto;
    }

    public CategoriaDTO obtener(Integer id) {
        CategoriaModel categoria = repositorio.findById(id).orElse(null);
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        return dto;
    }

}
