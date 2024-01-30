package com.decode.web.domain.store.repository;

import com.decode.web.entity.ItemEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager entityManager;

    public ItemEntity findByProductIdAndUserInfoId(Long productId, Long userInfoId) {
        return entityManager.createQuery("select i "
                        + "from ItemEntity i "
                        + "join fetch i.product "
                        + "join fetch i.userInfo "
                        + "join fetch i.userInfo.userProfileEntity "
                        + "where i.product.id =: productId "
                        + "and  i.userInfo.id =: userInfoId", ItemEntity.class)
                .setParameter("productId", productId)
                .setParameter("userInfoId", userInfoId)
                .getSingleResult();
    }
}
