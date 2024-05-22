package com.pradera.praderaback.service;

import com.pradera.praderaback.dto.CategoriaDTO;
import com.pradera.praderaback.dto.KardexResponse;
import com.pradera.praderaback.dto.ProductoDTO;
import com.pradera.praderaback.dto.SalidaDTO;
import com.pradera.praderaback.model.CategoriaModel;
import com.pradera.praderaback.model.ProductoModel;
import com.pradera.praderaback.repository.CategoriaRepository;
import com.pradera.praderaback.repository.ProductoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager em;
    private CriteriaBuilder cb;
    private CriteriaQuery<ProductoModel> cq;
    private Root<ProductoModel> root;
    private CriteriaQuery<Long> cqCont;
    private Root<ProductoModel> rootCont;

    public void initCb(){
        cb = em.getCriteriaBuilder();
        cq = cb.createQuery(ProductoModel.class);
        root = cq.from(ProductoModel.class);
        cqCont = cb.createQuery(Long.class);
        rootCont = cqCont.from(ProductoModel.class);
    }
    public TypedQuery<ProductoModel> filtrado(ProductoDTO filtro) {
        initCb();
        cqCont.select(cb.count(rootCont));
        Predicate[] predicatesArray;
        var predicates = new ArrayList<Predicate>();
        if (filtro.getNombre() != null) {
            predicates.add(cb.equal(root.get("nombre"),filtro.getNombre()));
        }
        predicatesArray = predicates.toArray(new Predicate[0]);
        cq.where(predicatesArray);
        cqCont.where(predicatesArray);
        cq.select(root).distinct(true);
        return  em.createQuery(cq);
    }
    public Page<ProductoDTO> bandeja(ProductoDTO filtro, Integer page, Integer size ){
        var result = this.filtrado(filtro);
        var resultCont = em.createQuery(cqCont);
        Long all = resultCont.getSingleResult();
        result = result.setFirstResult(page);
        result = result.setMaxResults(size);
        var resultList = result.getResultList();
        em.close();
        List<ProductoDTO> response = resultList.stream().map(x ->
                modelMapper.map(x, ProductoDTO.class)
        ).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(response, pageable, all);
    }

    public ProductoDTO obtener(Long id) {
        ProductoModel producto = repository.findById(id).orElse(null);
        return modelMapper.map(producto, ProductoDTO.class);
    }

    public List<ProductoDTO> listar() {
        List<ProductoDTO> listadto = new ArrayList<>();
        List<ProductoModel> listamodelo = repository.findAll();
        for (ProductoModel producto : listamodelo
        ) {
            ProductoDTO dto = modelMapper.map(producto, ProductoDTO.class);
            listadto.add(dto);
        }
        return listadto;
    }

    public void guardar(ProductoDTO dto) {
        ProductoModel producto = new ProductoModel();
        CategoriaModel categoria = categoriaRepository.findById(dto.getCategoriaId()).orElse(null);
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setPresentacion(dto.getPresentacion());
        producto.setCategoria(categoria);
        repository.save(producto);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public List<KardexResponse> consumos(){
        List<Tuple> response = repository.findEntradasSalidas();
        List<KardexResponse> lista = new ArrayList<>();
        for (Tuple data : response){
            KardexResponse kardex = new KardexResponse();
            kardex.setProducto(String.valueOf(data.get("Producto")));
            kardex.setEntradas(Integer.parseInt(String.valueOf(data.get("Entradas"))));
            kardex.setSalidas(Integer.parseInt(String.valueOf(data.get("Salidas"))));
            kardex.setStock(Integer.parseInt(String.valueOf(data.get("Stock"))));
            lista.add(kardex);
        }
        return lista;
    }

}
