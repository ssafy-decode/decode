package com.decode.web.domain.product.repository;

import com.decode.web.entity.ProductEntity;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final EntityManager entityManager;

    public List<ProductEntity> findAll() {
        return entityManager.createQuery("select p "
                        + "from ProductEntity p "
                        + "where p.isSale = true", ProductEntity.class)
                .getResultList();
    }

}
