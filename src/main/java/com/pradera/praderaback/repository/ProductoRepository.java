package com.pradera.praderaback.repository;

import com.pradera.praderaback.model.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel, Long> {
}
