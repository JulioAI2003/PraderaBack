package com.pradera.praderaback.service;

import com.pradera.praderaback.dto.SalidaDTO;
import com.pradera.praderaback.model.IngresosModel;
import com.pradera.praderaback.model.ProductoModel;
import com.pradera.praderaback.model.SalidaModel;
import com.pradera.praderaback.model.TrabajadorModel;
import com.pradera.praderaback.repository.ProductoRepository;
import com.pradera.praderaback.repository.SalidaRepository;
import com.pradera.praderaback.repository.TrabajadorRepository;
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
public class SalidaService {

    @Autowired
    private SalidaRepository repository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private TrabajadorRepository trabajadorRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager em;
    private CriteriaBuilder cb;
    private CriteriaQuery<SalidaModel> cq;
    private Root<SalidaModel> root;
    private CriteriaQuery<Long> cqCont;
    private Root<SalidaModel> rootCont;

    public void initCb(){
        cb = em.getCriteriaBuilder();
        cq = cb.createQuery(SalidaModel.class);
        root = cq.from(SalidaModel.class);
        cqCont = cb.createQuery(Long.class);
        rootCont = cqCont.from(SalidaModel.class);
    }

    public TypedQuery<SalidaModel> filtrado(SalidaDTO filtro) {
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

    public Page<SalidaDTO> bandeja(SalidaDTO filtro, Integer page, Integer size ){
        var result = this.filtrado(filtro);
        var resultCont = em.createQuery(cqCont);
        Long all = resultCont.getSingleResult();
        result = result.setFirstResult(page);
        result = result.setMaxResults(size);
        var resultList = result.getResultList();
        em.close();
        List<SalidaDTO> response = resultList.stream().map(x ->
                modelMapper.map(x, SalidaDTO.class)
        ).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(response, pageable, all);
    }

    public SalidaDTO obtener(Long id) {
        SalidaModel salida = repository.findById(id).orElse(null);
        return modelMapper.map(salida, SalidaDTO.class);
    }

    public List<SalidaDTO> listar() {
        List<SalidaDTO> listadto = new ArrayList<>();
        List<SalidaModel> listamodelo = repository.findAll();
        for (SalidaModel salida : listamodelo
        ) {
            SalidaDTO dto = modelMapper.map(salida, SalidaDTO.class);
            listadto.add(dto);
        }
        return listadto;
    }

    public void guardar(SalidaDTO dto) {
//        ProductoModel producto = productoRepository.findById(dto.getProductoId()).orElse(null);
//        TrabajadorModel trabajador = trabajadorRepository.findById(dto.getTrabajador().getId()).orElse(null);
//        SalidaModel salida = new SalidaModel();
//        salida.setId(dto.getId());
//        salida.setProducto(producto);
//        salida.setTrabajador(trabajador);
//        salida.setCantidad(dto.getCantidad());
//        salida.setFecha(dto.getFecha());

        SalidaModel salida = new SalidaModel();
        ProductoModel producto = new ProductoModel();
        producto.setId(dto.getProductoId());
        TrabajadorModel trabajador = new TrabajadorModel();
        trabajador.setId(dto.getTrabajadorId());
//        salida.setId(dto.getId());
        salida.setId(43434345L);//prueba
        salida.setCantidad(dto.getCantidad());
        salida.setProducto(producto);
        salida.setTrabajador(trabajador);
        repository.save(salida);

    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
