package com.deyuan.service;

import com.deyuan.pojo.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();

    void save(Product product);
}
