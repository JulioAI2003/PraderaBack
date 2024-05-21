package com.pradera.praderaback.service;

import com.pradera.praderaback.dto.ProductoDTO;
import com.pradera.praderaback.dto.ProveedorDTO;
import com.pradera.praderaback.model.CategoriaModel;
import com.pradera.praderaback.model.ProductoModel;
import com.pradera.praderaback.model.ProveedorModel;
import com.pradera.praderaback.repository.CategoriaRepository;
import com.pradera.praderaback.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ProductoDTO obtener(Long id) {
        ProductoModel producto = repository.findById(id).orElse(null);
        ProductoDTO dto = null;
        if (producto != null){
            dto = new ProductoDTO();
            dto.setId(producto.getId());
            dto.setNombre(producto.getNombre());
            dto.setPresentacion(producto.getPresentacion());
            dto.setCategoria(producto.getCategoria());
        }
        return dto;
    }

    public List<ProductoDTO> listar() {
        List<ProductoDTO> listadto = new ArrayList<>();
        List<ProductoModel> listamodelo = repository.findAll();
        for (ProductoModel producto : listamodelo
        ) {
            ProductoDTO dto = new ProductoDTO();
            dto.setId(producto.getId());
            dto.setNombre(producto.getNombre());
            dto.setPresentacion(producto.getPresentacion());
            dto.setCategoria(producto.getCategoria());
            listadto.add(dto);
        }
        return listadto;
    }

    public void guardar(ProductoDTO dto) {
        ProductoModel producto = new ProductoModel();
        CategoriaModel categoria = categoriaRepository.findById(dto.getCategoria().getId()).orElse(null);
        if(dto.getId() != null){
            producto.setId(dto.getId());
        }
        producto.setNombre(dto.getNombre());
        producto.setPresentacion(dto.getPresentacion());
        producto.setCategoria(categoria);
        repository.save(producto);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
