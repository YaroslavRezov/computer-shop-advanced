package com.example.computershop;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, String> {

    @Query(nativeQuery = true, value = """
            SELECT product.model, maker, type, code
            FROM
                (SELECT model, code
                 FROM  laptop
                 UNION
                 SELECT model, code
                 FROM printer
                 UNION
                 SELECT model, code
                 FROM pc)as bruh
                    JOIN product ON bruh.model=product.model;""")
    Iterable<ProductJoinedView> findAllProductsJoined();


//    @SQLInsert(sql = """
//                INSERT INTO product(model, type, maker) values (model, type, maker);""")
//    ProductDto insertIntoProduct(ProductEntity productEntity);
    

}
