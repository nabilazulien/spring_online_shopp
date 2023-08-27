package TechincalTest.Example.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "code")
    private String code;

    @Column(name = "product_name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "qty_stock")
    private int qty;

    @Column(name = "is_active")
    private boolean active;
}
