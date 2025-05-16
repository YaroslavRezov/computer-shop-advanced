package com.example.computershop.repositoryCustomer;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.ProductJoinedView;
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
                 FROM pc
                 ORDER BY code)as bruh
                    RIGHT OUTER JOIN product ON bruh.model=product.model;""")
    Iterable<ProductJoinedView> findAllProductsJoined();


//    @SQLInsert(sql = """
//                INSERT INTO product(model, type, maker) values (model, type, maker);""")
//    ProductDto insertIntoProduct(ProductEntity productEntity);
    

}
