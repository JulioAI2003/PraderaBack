package com.pradera.praderaback.service;

import com.pradera.praderaback.dto.IngresoDTO;
import com.pradera.praderaback.model.IngresosModel;
import com.pradera.praderaback.model.ProductoModel;
import com.pradera.praderaback.repository.IngresoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngresoService {
    @Autowired
    private IngresoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager em;
    private CriteriaBuilder cb;
    private CriteriaQuery<IngresosModel> cq;
    private Root<IngresosModel> root;
    private CriteriaQuery<Long> cqCont;
    private Root<IngresosModel> rootCont;

    public void initCb(){
        cb = em.getCriteriaBuilder();
        cq = cb.createQuery(IngresosModel.class);
        root = cq.from(IngresosModel.class);
        cqCont = cb.createQuery(Long.class);
        rootCont = cqCont.from(IngresosModel.class);
    }

    public Page<IngresoDTO> bandeja(IngresoDTO filtro, Integer page, Integer size ){
        var result = this.filtrado(filtro);
        var resultCont = em.createQuery(cqCont);
        Long all = resultCont.getSingleResult();
        result = result.setFirstResult(page);
        result = result.setMaxResults(size);
        var resultList = result.getResultList();
        em.close();
        List<IngresoDTO> response = resultList.stream().map(x ->
                modelMapper.map(x, IngresoDTO.class)
        ).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(response, pageable, all);
    }
    public TypedQuery<IngresosModel> filtrado(IngresoDTO filtro) {
        initCb();
        cqCont.select(cb.count(rootCont));
        Predicate[] predicatesArray;
        var predicates = new ArrayList<Predicate>();
        if (filtro.getProductoNombre() != null) {
            predicates.add(cb.equal(root.get("producto").get("nombre"),filtro.getProductoNombre()));
        }
        predicatesArray = predicates.toArray(new Predicate[0]);
        cq.where(predicatesArray);
        cqCont.where(predicatesArray);
        cq.select(root).distinct(true);
        return  em.createQuery(cq);
    }

    public void guardar(IngresoDTO dto) {
        IngresosModel ingreso = new IngresosModel();
        ProductoModel producto = new ProductoModel();
        producto.setId(dto.getProductoId());

        ingreso.setProducto(producto);
        ingreso.setCantidad(dto.getCantidad());
        ingreso.setFecha(dto.getFecha());
        repository.save(ingreso);
    }

    public IngresoDTO findById(Long id) {
        IngresosModel ingreso = repository.findById(id).orElse(null);
        return modelMapper.map(ingreso, IngresoDTO.class);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

}
