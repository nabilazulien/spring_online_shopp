package TechincalTest.Example.controller;

import TechincalTest.Example.dto.CartRequest;
import TechincalTest.Example.service.CartTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    CartTransaction cartTransaction;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cartAdd(@RequestHeader String userId, @RequestBody CartRequest cartRequest) {
        return cartTransaction.cartAdd(cartRequest,userId);
    }

    @PostMapping(value = "/add/more", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cartAddMore(@RequestHeader String userId, @RequestBody CartRequest cartRequest) {
        return cartTransaction.cartAdd(cartRequest,userId);
    }

    @PostMapping(value = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> remove1(@RequestHeader String userId, @RequestBody CartRequest cartRequest) {
        return cartTransaction.remove(cartRequest,userId);
    }

    @GetMapping(value = "/finalize", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> remove1(@RequestHeader String userId) {
        return cartTransaction.total(userId);
    }

}
