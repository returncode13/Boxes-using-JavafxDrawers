/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author sharath nair <sharath.nair@polarcus.com>
 */
//@Entity
@Table(name="Item",schema = "obpmanager")
public class Item{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    @Column(name="item_name")
    private String itemName;
    
    @Column(name="item_total")
    private Double itemTotal;
    
    @Column(name="quantity")
    private Double quantity;
    
    @ManyToOne
    @JoinColumn(name="cart_fk",nullable=false)
    private Cart cart;
    
    public Item(){}

    public Item(String itemName, Double itemTotal, Double quantity, Cart cart) {
        this.itemName = itemName;
        this.itemTotal = itemTotal;
        this.quantity = quantity;
        this.cart = cart;
    }
    
    
    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(Double itemTotal) {
        this.itemTotal = itemTotal;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
    
    
    
}
