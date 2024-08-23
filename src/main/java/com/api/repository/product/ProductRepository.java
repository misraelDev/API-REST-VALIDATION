package com.api.repository.product;

import com.api.model.product.Product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Checks if a product exists in the repository based on the provided name.
     * 
     * @param name the name of the product to check
     * @return true if the product exists, false otherwise
     */
    boolean existsProductByName(String name);

    /**
     * Finds a product in the repository based on the provided name and ID.
     * The product found must not have the same ID as the one provided.
     * 
     * @param name the name of the product to find
     * @param idProduct the ID of the product to exclude from the search
     * @return the product if found, empty otherwise
     */
    Optional<Product> findByNameAndIdProductNot(String name, Long idProduct);
}

/*
    { "name": "Fruits", "description": "Fresh fruits" }
    { "name": "Vegetables", "description": "Fresh vegetables" }
    { "name": "Electronics", "description": "Electronics products" }
    { "name": "Clothing", "description": "Clothing for men and women" }
    { "name": "Home and Garden", "description": "Home and garden products" }
    { "name": "Books", "description": "Books in various categories" }
    { "name": "Toys and Games", "description": "Toys and games for children" }
    { "name": "Sports and Fitness", "description": "Sports and fitness products" }
    { "name": "Beauty and Personal Care", "description": "Beauty and personal care products" }
    { "name": "Health and Wellness", "description": "Health and wellness products" }
*/

/*
{
    "name": "Apple iPhone 13",
    "description": "Apple iPhone 13 smartphone. Announced Sep 2020. Features 6.1 inch display, Apple A14 Bionic Hexa Core processor, 12 MP back camera, 12 MP front camera, 64 GB storage, 4 GB RAM, iOS 14.",
    "totalQuantity": 1,
    "price": 799.99,
    "idCategory": 3
  },
  {
    "name": "Samsung TV 50",
    "description": "Samsung TV 50 inch. 4K UHD Smart TV. Wi-Fi, 3 HDMI, 2 USB.",
    "totalQuantity": 2,
    "price": 499.99,
    "idCategory": 3
  },
  {
    "name": "Nike Air Force 1",
    "description": "Nike Air Force 1 men's shoe. White/Black.",
    "totalQuantity": 5,
    "price": 89.99,
    "idCategory": 4
  },
  {
    "name": "Sony PlayStation 5",
    "description": "Sony PlayStation 5 console. 825 GB.",
    "totalQuantity": 3,
    "price": 399.99,
    "idCategory": 3
  },
  {
    "name": "Adidas Superstar",
    "description": "Adidas Superstar men's shoe. White/Black.",
    "totalQuantity": 5,
    "price": 79.99,
    "idCategory": 4
  },
  {
    "name": "Apple MacBook Air",
    "description": "Apple MacBook Air laptop. 13.3 inch, Intel Core i5, 8 GB RAM, 256 GB SSD.",
    "totalQuantity": 2,
    "price": 999.99,
    "idCategory": 3
  },
  {
    "name": "Nike Air Max 270",
    "description": "Nike Air Max 270 men's shoe. Black/White.",
    "totalQuantity": 5,
    "price": 109.99,
    "idCategory": 4
  },
  {
    "name": "Canon EOS Rebel",
    "description": "Canon EOS Rebel T8i camera. 18 MP, 4K video, Wi-Fi, NFC.",
    "totalQuantity": 2,
    "price": 749.99,
    "idCategory": 3
  },
  {
    "name": "Adidas Ultraboost",
    "description": "Adidas Ultraboost men's shoe. Black/White.",
    "totalQuantity": 5,
    "price": 179.99,
    "idCategory": 4
  },
  {
    "name": "Samsung Galaxy S21",
    "description": "Samsung Galaxy S21 smartphone. 6.2 inch, Android 11, 12 GB RAM, 128 GB storage.",
    "totalQuantity": 2,
    "price": 799.99,
    "idCategory": 3
  }
*/

