package TechincalTest.Example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "cart")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "code")
    private String code;

    @Column(name = "product_name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "qty_order")
    private double qtyOrder;

    @Column(name = "grand_total")
    private double grandTotal;


}
