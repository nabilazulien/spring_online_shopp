package TechincalTest.Example.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequest {
    private String code;
    private String name;
    private double price;
    private int qty;
}
