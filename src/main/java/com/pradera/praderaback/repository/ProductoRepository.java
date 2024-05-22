package com.pradera.praderaback.repository;

import com.pradera.praderaback.model.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel, Long> {

    @Query(value = "SELECT \n" +
            "    pp.nom_prod_pra AS Producto, \n" +
            "    COALESCE(SUM(ip.cant_ingr_pra), 0) AS Entradas, \n" +
            "    COALESCE(SUM(sp.cant_sali_pra), 0) AS Salidas,\n" +
            "    COALESCE(SUM(ip.cant_ingr_pra), 0) - COALESCE(SUM(sp.cant_sali_pra), 0) AS Stock\n" +
            "FROM prod_pra pp\n" +
            "LEFT JOIN (SELECT id_prod_pra, SUM(cant_ingr_pra) AS cant_ingr_pra \n" +
            "           FROM ingr_pra \n" +
            "           GROUP BY id_prod_pra) ip\n" +
            "    ON pp.id = ip.id_prod_pra\n" +
            "LEFT JOIN (SELECT id_producto, SUM(cant_sali_pra) AS cant_sali_pra \n" +
            "           FROM sali_pra \n" +
            "           GROUP BY id_producto) sp\n" +
            "    ON pp.id = sp.id_producto\n" +
            "GROUP BY pp.nom_prod_pra;", nativeQuery = true)
    List<Tuple> findEntradasSalidas();
}
