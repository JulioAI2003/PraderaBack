package com.pradera.praderaback.service;

import com.pradera.praderaback.dto.ProveedorDTO;
import com.pradera.praderaback.model.ProveedorModel;
import com.pradera.praderaback.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository repository;

    public ProveedorDTO obtener(Long id) {
        ProveedorModel proveedor = repository.findById(id).orElse(null);
        ProveedorDTO dto = null;
        if (proveedor != null){
            dto = new ProveedorDTO();
            dto.setId(proveedor.getId());
            dto.setNombre(proveedor.getNombre());
            dto.setRuc(proveedor.getRuc());
        }
        return dto;
    }

    public List<ProveedorDTO> listar() {
        List<ProveedorDTO> listadto = new ArrayList<>();
        List<ProveedorModel> listamodelo = repository.findAll();
        for (ProveedorModel proveedor : listamodelo
        ) {
            ProveedorDTO dto = new ProveedorDTO();
            dto.setId(proveedor.getId());
            dto.setNombre(proveedor.getNombre());
            dto.setRuc(proveedor.getRuc());
            listadto.add(dto);
        }
        return listadto;
    }

    public void guardar(ProveedorDTO dto) {
        ProveedorModel proveedor = new ProveedorModel();
        if(dto.getId() != null){
            proveedor.setId(dto.getId());
        }
        proveedor.setNombre(dto.getNombre());
        proveedor.setRuc(dto.getRuc());
        repository.save(proveedor);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

}
