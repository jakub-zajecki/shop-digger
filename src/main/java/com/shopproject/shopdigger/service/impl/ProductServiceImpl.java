package com.shopproject.shopdigger.service.impl;

import com.shopproject.shopdigger.converters.ProductConverter;
import com.shopproject.shopdigger.converters.UserConverter;
import com.shopproject.shopdigger.dao.ProductRepository;
import com.shopproject.shopdigger.dto.ProductDto;
import com.shopproject.shopdigger.model.Product;
import com.shopproject.shopdigger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductConverter productConverter;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    public List<Product> getAllProducts(){
        List<Product> result = new ArrayList<>();
        Iterable<Product> iterable = productRepository.findAll();
        iterable.forEach(product -> result.add(product));
        return result;
    }

    @Override
    public Optional<Product> getProductById(Long productId){
        return productRepository.findById(productId);
    }

    @Override
    public boolean saveProduct(Product product) {
        productRepository.save(product);
        return true;
    }

    @Override
    public Page<Product> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Product> list;
        List<Product> products = getAllProducts();

        if (products.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, products.size());
            list = products.subList(startItem, toIndex);
        }

        Page<Product> productPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), products.size());

        return productPage;
    }

    @Override
    public boolean deleteProduct(Product product) {
        productRepository.delete(product);
        return true;
    }

    @Override
    public List<ProductDto> getHighlightedProducts() {
        List<Product> products = productRepository.findProductsByHighlightedTrue();
        List<ProductDto> productDtos = new ArrayList<>();
        products.forEach(product -> productDtos.add(productConverter.convertDto(product)));
        return productDtos;
    }

    @Override
    public boolean setHighlighted(Long id, boolean choice) {
        Optional<Product> product = getProductById(id);
        System.out.println(id);
        if(product.isPresent()){
            Product finalProduct = product.get();
            finalProduct.setHighlighted(choice);
            System.out.println(finalProduct.isHighlighted());
            saveProduct(finalProduct);
            System.out.println("Zapisano " + id);
        }
        return true;
    }

    @Override
    public List<ProductDto> generateIndexProducts() {
        List<ProductDto> list = getHighlightedProducts();
        List<ProductDto> finalList = new ArrayList<>();
        Random random = new Random(list.size());
        while(finalList.size() <= 10) {
            int index = random.nextInt();
            if (!finalList.contains(list.get(index))) {
                finalList.add(list.get(index));
            }
        }
        return finalList;
    }
}
