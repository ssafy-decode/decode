package com.decode.web.entity;

import com.decode.web.domain.store.dto.ProductDto;
import com.decode.web.global.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Table(name = "Product")
public class ProductEntity extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    @Size(max = 100)
    private String productName;

    @Column(name = "product_price")
    private Integer productPrice;

    @Column(name = "product_detail")
    @Size(max = 500)
    private String productDetail;

    @Column(name = "product_type")
    private Integer productType;

    @Column(name = "product_iamge")
    private String productImageUrl;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)", name = "is_sale")
    private boolean isSale;

    public void buy(int count, ItemEntity itemEntity, UserProfileEntity userProfile) {
        int totalAmount = this.productPrice * count;
        userProfile.decreaseCoin(totalAmount);
        itemEntity.increaseProductCount(count);
    }

    public ProductDto toDto() {
        return ProductDto.builder()
                .productId(id)
                .productImage(productImageUrl)
                .productDetail(productDetail)
                .productName(productName)
                .productPrice(productPrice)
                .productType(productType)
                .build();
    }
}
