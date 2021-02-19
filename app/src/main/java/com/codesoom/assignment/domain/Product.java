package com.codesoom.assignment.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    private String maker;

    private int price;

    private String imageUrl;

    public Product() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Builder
    private Product(Long id, String name, String maker, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    /**
     * 상품을 수정하고, 수정된 상품을 리턴합니다.
     *
     * @param product 수정하고자 하는 상품
     * @return 수정된 상품
     */
    public Product update(Product product) {
        this.name = product.getName();
        this.maker = product.getMaker();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();

        return this;
    }

}

