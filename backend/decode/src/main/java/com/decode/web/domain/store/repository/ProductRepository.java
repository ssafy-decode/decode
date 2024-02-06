package com.decode.web.domain.store.repository;

import com.decode.web.entity.ProductEntity;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final EntityManager entityManager;

    public ProductEntity findById(Long productId) {
        return entityManager.createQuery("select p from ProductEntity p "
                        + "where p.id =: productId", ProductEntity.class)
                .setParameter("productId", productId)
                .getSingleResult();
    }

    public List<ProductEntity> findAll() {
        return entityManager.createQuery("select p "
                        + "from ProductEntity p "
                        + "where p.isSale = true", ProductEntity.class)
                .getResultList();
    }

    public List<ProductEntity> findByName(String productName) {
        return entityManager.createQuery("select p from ProductEntity p "
                        + "where p.productName like :productName", ProductEntity.class)
                .setParameter("productName", productName)
                .getResultList();
    }

}
