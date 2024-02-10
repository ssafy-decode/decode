package com.decode.web.entity;

import com.decode.web.domain.store.dto.ItemDto;
import com.decode.web.exception.NotEnoughCountException;
import com.decode.web.global.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Item")
public class ItemEntity extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserInfoEntity userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "product_count")
    private Integer productCount;

    public void increaseProductCount(int count) {
        this.productCount += count;
    }

//    @ColumnDefault("false")
//    @Column(columnDefinition = "TINYINT(1)", name = "is_using")
//    private boolean isUsing;

    public ItemDto toDto() {
        return ItemDto.builder()
                .itemName(product.getProductName())
                .itemDetail(product.getProductDetail())
                .itemId(id)
                .itemCount(productCount)
                .build();
    }

    public void buy(int count) {
        if (productCount - count < 0) {
            throw new NotEnoughCountException("아이템 개수가 부족합니다.");
        }
        productCount -= count;
    }
}
