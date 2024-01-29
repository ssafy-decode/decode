package com.decode.web.entity;

import com.decode.web.global.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
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

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)", name = "is_sale")
    private boolean isSale;

}
