package TechincalTest.Example.service;

import TechincalTest.Example.dto.ProductRequest;
import TechincalTest.Example.model.Product;
import TechincalTest.Example.util.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<Response> createProduct(ProductRequest productRequest);

    List<Product> findAllProduct();

    ResponseEntity<Response> searchProduct(String name);


}