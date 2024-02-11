package com.decode.web.domain.store.repository;

import com.decode.web.entity.ItemEntity;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager entityManager;

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

    public Optional<ItemEntity> findByItemIdAndUserId(Long itemId, Long userInfoId) {
        return entityManager.createQuery("select i "
                        + "from ItemEntity i "
                        + "join fetch i.userInfo "
                        + "where i.id =: itemId "
                        + "and  i.userInfo.id =: userInfoId", ItemEntity.class)
                .setParameter("itemId", itemId)
                .setParameter("userInfoId", userInfoId)
                .getResultList()
                .stream()
                .findAny();
    }

    public ItemEntity findByProductIdAndUserId(Long productId, Long userId) {
        return entityManager.createQuery("select i from ItemEntity i "
                        + "join fetch i.product "
                        + "join fetch i.userInfo "
                        + "where i.product.id =: productId and i.userInfo.id =: userId", ItemEntity.class)
                .setParameter("productId", productId)
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(ItemEntity.builder().productCount(0).build());
    }

    public void save(ItemEntity item) {
        entityManager.persist(item);
    }
}
