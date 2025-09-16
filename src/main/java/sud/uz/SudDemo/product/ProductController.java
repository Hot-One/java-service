package sud.uz.SudDemo.product;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import sud.uz.SudDemo.category.Category;
import sud.uz.SudDemo.product.dto.ProductDto;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create a new product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto input) {
        Product product = new Product();
        product.setName(input.getName());
        product.setDescription(input.getDescription());
        product.setPrice(input.getPrice());
        product.setQuantity(input.getQuantity());

        if (input.getCategoryId() != null) {
            Category category = new Category();
            category.setId(input.getCategoryId());
            product.setCategory(category);
        }

        Product response = productService.createProduct(product);
        return ResponseEntity.created(null).body(response);
    }

    @Operation(summary = "Get single product by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product response = productService.getProductById(id);
        if (response == null) {
            throw new jakarta.persistence.EntityNotFoundException("Product not found with id: " + id);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get list of products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<java.util.List<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(productService.getAllProducts(offset, limit));
    }

    @Operation(summary = "Update product by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product response = productService.updateProduct(id, updatedProduct);
        if (response == null) {
            throw new jakarta.persistence.EntityNotFoundException("Product not found with id: " + id);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete product by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}  
