package TechincalTest.Example.service;

import TechincalTest.Example.dto.CartRequest;
import TechincalTest.Example.util.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CartTransaction {

    ResponseEntity<Response> cartAdd(CartRequest req,String userId);

    ResponseEntity<Response> remove(CartRequest req,String userId);
    ResponseEntity<Response> total(String userId);
}
