package com.example.computershop.repository;
import com.example.computershop.model.entity.ProductEntity;
import com.example.computershop.model.entity.ProductJoinedView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {

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
    List<ProductJoinedView> findAllProductsJoined();


//    @SQLInsert(sql = """
//                INSERT INTO product(model, type, maker) values (model, type, maker);""")
//    ProductDto insertIntoProduct(ProductEntity productEntity);
    
    // there is a bug we need to delete cascade because if cart has the item, its imposible to delete it
    // via admin func ERROR: update or delete on table "product" violates foreign key constraint "cart_product_model_fk" on table "cart"
}
