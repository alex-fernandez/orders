package com.alexfrndz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_lines")
public class OrderLineEntity extends DomainObject {

    @ManyToOne
    private OrderEntity order;

    @Column(name = "qty", length = 10)
    private int qty;

    @Column(name = "product", length = 100)
    private String product;

    @Column(name = "price")
    private Double price;


    public enum Paths {
        order, qty, product, id, price
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


}
