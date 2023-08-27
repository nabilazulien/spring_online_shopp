package TechincalTest.Example.service;

import TechincalTest.Example.dto.CartRequest;
import TechincalTest.Example.model.Cart;
import TechincalTest.Example.model.Product;
import TechincalTest.Example.repository.CartRepository;
import TechincalTest.Example.repository.ProductRepository;
import TechincalTest.Example.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartTransactionImpl implements CartTransaction {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public ResponseEntity<Response> cartAdd(CartRequest req, String userId) {
        log.info("cart add service");
        if(userId==""){
            return new ResponseEntity<>(new Response("400", "Gagal", "user id tidak boleh kosong"), HttpStatus.resolve(HttpStatus.OK.value()));
        }
        if(userId==null){
            return new ResponseEntity<>(new Response("400", "Gagal", "user id tidak boleh null"), HttpStatus.resolve(HttpStatus.OK.value()));
        }

        String userIdLogin = (userId);
        Optional<Product> byId = productRepository.findById(req.getItemId());
        if (byId.isEmpty()) {
            return new ResponseEntity<>(new Response("400", "Gagal", "id product tidak terdaftar"), HttpStatus.resolve(HttpStatus.OK.value()));
        }
        List<Cart> byUserId = cartRepository.findByUserId(userIdLogin);
        if (byUserId.isEmpty()) {
            newSaveDataCart(req, userIdLogin, byId);
            return new ResponseEntity<>(new Response("200", "SUCCESS", "Data berhaasil disimpan"), HttpStatus.resolve(HttpStatus.OK.value()));
        }
        boolean a = true;
        for (Cart p : byUserId) {
            if (p.getCode().equals(byId.get().getCode())) {
                a = false;
                double qty = p.getQtyOrder() + req.getQty();
                double total = qty * byId.get().getPrice();
                p.setQtyOrder(qty);
                p.setGrandTotal(total);
                cartRepository.save(p);
            }
        }
        if (a) {
            newSaveDataCart(req, userIdLogin, byId);
        }
        return new ResponseEntity<>(new Response("200", "SUCCESS", "Data berhaasil disimpan"), HttpStatus.resolve(HttpStatus.OK.value()));

    }

    @Override
    public ResponseEntity<Response> remove(CartRequest req, String userId) {
        if(userId==""){
            return new ResponseEntity<>(new Response("400", "Gagal", "user id tidak boleh kosong"), HttpStatus.resolve(HttpStatus.OK.value()));
        }
        if(userId==null){
            return new ResponseEntity<>(new Response("400", "Gagal", "user id tidak boleh null"), HttpStatus.resolve(HttpStatus.OK.value()));
        }
        List<Cart> byUserId = cartRepository.findByUserId(userId);
        if (byUserId.isEmpty()) {
            return new ResponseEntity<>(new Response("400", "Gagal", "Anda tidak memiliki produk dalam keranjang"), HttpStatus.resolve(HttpStatus.OK.value()));
        }
        Optional<Product> byId = productRepository.findById(req.getItemId());
        if (byId.isEmpty()) {
            return new ResponseEntity<>(new Response("400", "Gagal", "id product tidak terdaftar"), HttpStatus.resolve(HttpStatus.OK.value()));
        }

        for (Cart p : byUserId) {
            if (p.getCode().equals(byId.get().getCode())) {
                cartRepository.delete(p);
                return new ResponseEntity<>(new Response("200", "SUCCESS", "Succes delete"), HttpStatus.resolve(HttpStatus.OK.value()));

            }
        }
        return new ResponseEntity<>(new Response("400", "Gagal", "id produk tidak ada dalam keranjang anda"), HttpStatus.resolve(HttpStatus.OK.value()));


    }

    @Override
    public ResponseEntity<Response> total(String userId) {
        if(userId==""){
            return new ResponseEntity<>(new Response("400", "Gagal", "user id tidak boleh kosong"), HttpStatus.resolve(HttpStatus.OK.value()));
        }
        if(userId==null){
            return new ResponseEntity<>(new Response("400", "Gagal", "user id tidak boleh null"), HttpStatus.resolve(HttpStatus.OK.value()));
        }
        List<Cart> byUserId = cartRepository.findByUserId(userId);
        if (byUserId.isEmpty()) {
            return new ResponseEntity<>(new Response("400", "Gagal", "Anda tidak memiliki produk dalam keranjang"), HttpStatus.resolve(HttpStatus.OK.value()));
        }
        int jmlhItem = 0;
        double total = 0;
        for (Cart p : byUserId) {
            jmlhItem += p.getQtyOrder();
            total += p.getGrandTotal();
        }
        return new ResponseEntity<>(new Response("200", "SUCCESS", "total item ="+jmlhItem+" , total harga="+total), HttpStatus.resolve(HttpStatus.OK.value()));
    }

    private void newSaveDataCart(CartRequest req, String userIdLogin, Optional<Product> byId) {
        double total = req.getQty() * byId.get().getPrice();
        Cart cart = new Cart();
        cart.setUserId(userIdLogin);
        cart.setCode(byId.get().getCode());
        cart.setName(byId.get().getName());
        cart.setPrice(byId.get().getPrice());
        cart.setQtyOrder(req.getQty());
        cart.setGrandTotal(total);
        cartRepository.save(cart);
    }
}
