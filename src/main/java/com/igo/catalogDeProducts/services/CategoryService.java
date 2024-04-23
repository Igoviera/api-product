package com.igo.catalogDeProducts.services;

import com.igo.catalogDeProducts.DTO.CategoryDTO;
import com.igo.catalogDeProducts.models.Category;
import com.igo.catalogDeProducts.respositories.CategoryRepository;
import com.igo.catalogDeProducts.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<CategoryDTO> findAllPaged(Pageable pageable){
        Page<Category> list = categoryRepository.findAll(pageable);
        return list.map(x -> new CategoryDTO(x));
    }

    public CategoryDTO findById(Long id){
        Optional<Category> obj = categoryRepository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        return new CategoryDTO(entity);
    }

    public CategoryDTO insert(CategoryDTO dto) {
       Category entity = new Category();
       entity.setName(dto.getName());
       entity = categoryRepository.save(entity);
       return  new CategoryDTO(entity);
    }

    public CategoryDTO update(CategoryDTO dto, Long id) {
         Optional<Category> categoryEncontrada = categoryRepository.findById(id);

         if (categoryEncontrada.isPresent()){
             Category category = categoryEncontrada.get();
             category.setName(dto.getName());
             categoryRepository.save(category);

             return new CategoryDTO(category);
         } else {
             throw new ResourceNotFoundException(id);
         }
    }

    public void delete(Long id){
        try{
            categoryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }
    }

}
