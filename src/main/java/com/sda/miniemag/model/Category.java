package com.sda.miniemag.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String photoUrl;


    @OneToMany(mappedBy = "category")
    private Set<Product> productSet;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private Set<Category> subcategories = new HashSet<Category>();

    public Category() {
    }

    public Category(String name, String photoUrl, Set<Product> productSet, Category parentCategory) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.productSet = productSet;
        this.parentCategory = parentCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }
    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<Category> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<Category> subcategories) {
        this.subcategories = subcategories;
    }
}
