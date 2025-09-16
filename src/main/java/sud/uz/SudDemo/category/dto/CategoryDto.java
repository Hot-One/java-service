package sud.uz.SudDemo.category.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private String name;
    private String description;
    private Long parentId;
}
