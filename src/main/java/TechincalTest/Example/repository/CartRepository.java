package TechincalTest.Example.repository;

import TechincalTest.Example.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {
List<Cart> findByUserId (String userId);
}
