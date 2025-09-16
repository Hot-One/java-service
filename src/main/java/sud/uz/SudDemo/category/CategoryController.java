package sud.uz.SudDemo.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import sud.uz.SudDemo.category.dto.CategoryDto;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Create a new category")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Category created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto input) {
        Category category = new Category();
        category.setName(input.getName());
        category.setDescription(input.getDescription());

        if (input.getParentId() != null) {
            Category parent = categoryService.getCategoryById(input.getParentId());
            if (parent == null) {
                throw new jakarta.persistence.EntityNotFoundException("Parent category not found");
            }
            category.setParent(parent);
        }

        Category response = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get single category by id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Category retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category response = categoryService.getCategoryById(id);
        if (response == null) {
            throw new jakarta.persistence.EntityNotFoundException("Category not found with id: " + id);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get list of categories")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(categoryService.getAllCategories(offset, limit));
    }

    @Operation(summary = "Update category by id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Category updated successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        Category response = categoryService.updateCategory(id, updatedCategory);
        if (response == null) {
            throw new jakarta.persistence.EntityNotFoundException("Category not found with id: " + id);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete category by id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
