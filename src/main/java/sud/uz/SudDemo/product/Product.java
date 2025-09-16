package sud.uz.SudDemo.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sud.uz.SudDemo.category.Category;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false) // FK column
    private Category category;
}
