package com.igo.catalogDeProducts.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(Long id){
        super("Registro não encontrado com o id: " + id);
    }
}
