package sud.uz.SudDemo.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String imageUrl;
    private Long categoryId;
}
