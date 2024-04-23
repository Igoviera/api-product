package repositories;

import com.igo.catalogDeProducts.models.Product;
import com.igo.catalogDeProducts.respositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest // Testa apenas o repository
public class ProductRepositoryTests {

//    @Autowired
//    private ProductRepository productRepository;
//
//    private long exintingId;
//    private long nonExistingId;
//
//    @BeforeEach
//    void setUp() throws Exception{
//        exintingId = 1L;
//        nonExistingId = 2L;
//    }
//
//    @Test
//    public void deleteShouldDeleteObjectWhenIdExists(){
//        productRepository.deleteById(exintingId);
//
//        Optional<Product> result = productRepository.findById(1L);
//        Assertions.assertFalse(result.isPresent());
//    }
//
//    @Test
//    public void deleteShoulThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist(){
//
//        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
//            productRepository.deleteById(nonExistingId);
//        });
//    }
}
