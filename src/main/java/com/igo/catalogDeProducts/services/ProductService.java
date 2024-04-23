package com.igo.catalogDeProducts.services;

import com.igo.catalogDeProducts.DTO.CategoryDTO;
import com.igo.catalogDeProducts.DTO.ProductDTO;
import com.igo.catalogDeProducts.exceptions.ResourceNotFoundException;
import com.igo.catalogDeProducts.models.Category;
import com.igo.catalogDeProducts.models.Product;
import com.igo.catalogDeProducts.respositories.CategoryRepository;
import com.igo.catalogDeProducts.respositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<ProductDTO> findAllPage(Pageable pageable){
        Page<Product> list = productRepository.findAll(pageable);
        return list.map(x -> new ProductDTO(x));
    }

    public ProductDTO findById(Long id){
        Optional<Product> obj = productRepository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return new ProductDTO(entity, entity.getCategories());
    }

    public ProductDTO insert(ProductDTO dto){
       Product entity = new Product();
       copyDtoEntity(dto, entity);
       entity = productRepository.save(entity);
       return new ProductDTO(entity);
    }

    public ProductDTO update(Long id,ProductDTO dto){
        Optional<Product> productEncontrado = productRepository.findById(id);

        if(productEncontrado.isPresent()){
            Product entity = productEncontrado.get();
            copyDtoEntity(dto, entity);
            return new ProductDTO(entity);
        } else {
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Long id){
       productRepository.delete(productRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    private void copyDtoEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());

        entity.getCategories().clear();
        for (CategoryDTO catDto : dto.getCategories()){
            Category category = categoryRepository.getReferenceById(catDto.getId());
            entity.getCategories().add(category);
        }

    }

}
