package TechincalTest.Example.service;

import TechincalTest.Example.dto.ProductRequest;
import TechincalTest.Example.model.Product;
import TechincalTest.Example.repository.ProductRepository;
import TechincalTest.Example.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    Response response;

    @Override
    public ResponseEntity<Response> createProduct(ProductRequest productRequest) {
        try {
            Optional<Product> byCode = productRepository.findByCode(productRequest.getCode());
            if(byCode.isPresent()){
                return new ResponseEntity<>(new Response("400", "Gagal", "Item code sudah terdaftar"), HttpStatus.resolve(HttpStatus.BAD_REQUEST.value()));
            }
            Product product = new Product();
            product.setCode(productRequest.getCode());
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setQty(productRequest.getQty());
            product.setActive(product.isActive());
            productRepository.save(product);
            return new ResponseEntity<>(new Response("200", "SUCCESS", "product " + productRequest.getName() + " saved successfully"), HttpStatus.resolve(HttpStatus.OK.value()));
        } catch (Exception e) {
            return new ResponseEntity<>(new Response("500", "FAILED", "product " + productRequest.getName() + " product already exist"), HttpStatus.resolve(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @Override
    public List<Product> findAllProduct() {
        try {
            List<Product> products = new ArrayList<>();
            List<Product> productList = new ArrayList<>();
            productRepository.findAll().forEach(products::add);
            productList.addAll(products);
            for (Product p : products) {
                if (!p.isActive()) {
                    productList.remove(p);
                }
            }
            return productList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity<Response> searchProduct(String name) {

        List<Product> byNameContaining = productRepository.findByNameContaining(name);

        return new ResponseEntity<>(new Response("200", "SUCCESS", byNameContaining), HttpStatus.resolve(HttpStatus.OK.value()));
    }
}
