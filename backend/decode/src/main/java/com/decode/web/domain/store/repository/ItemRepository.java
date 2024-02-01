package com.decode.web.domain.store.repository;

import com.decode.web.domain.store.dto.ItemUseDto;
import com.decode.web.entity.ItemEntity;
import jakarta.persistence.EntityManager;
import java.util.List;
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

    public List<ItemEntity> findByUserInfoId(Long userInfoId) {
        return entityManager.createQuery("select i "
                                + "from ItemEntity i "
                                + "join fetch i.product "
                                + "join fetch i.userInfo "
                                + "join fetch i.userInfo.userProfileEntity "
                                + "where i.userInfo.id =: userInfoId "
                        , ItemEntity.class)
                .setParameter("userInfoId", userInfoId)
                .getResultList();
    }

    public ItemEntity findByItemIdAndUserId(Long itemId, Long userInfoId) {
        return entityManager.createQuery("select i "
                        + "from ItemEntity i "
                        + "join fetch i.userInfo "
                        + "where i.id =: itemId "
                        + "and  i.userInfo.id =: userInfoId", ItemEntity.class)
                .setParameter("itemId", itemId)
                .setParameter("userInfoId", userInfoId)
                .getSingleResult();
    }
}
