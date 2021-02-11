package com.sda.miniemag.service;

import com.sda.miniemag.model.Cart;

import java.util.Comparator;

public class CartComparator  implements Comparator<Cart> {
    @Override
    public int compare(Cart o1, Cart o2) {
        return o2.getId().compareTo(o1.getId());
    }
}
