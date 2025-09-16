package sud.uz.SudDemo.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create product: " + e.getMessage(), e);
        }
    }

    public Product getProductById(Long id) {
        try {
            return productRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve product with id " + id + ": " + e.getMessage(), e);
        }
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        try {
            return productRepository.findById(id).map(product -> {
                product.setName(updatedProduct.getName());
                product.setDescription(updatedProduct.getDescription());
                product.setPrice(updatedProduct.getPrice());
                product.setCategory(updatedProduct.getCategory());
                product.setQuantity(updatedProduct.getQuantity());
                return productRepository.save(product);
            }).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product with id " + id + ": " + e.getMessage(), e);
        }
    }

    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product with id " + id + ": " + e.getMessage(), e);
        }
    }

    public List<Product> getAllProducts(int offset, int limit) {
        try {
            Pageable pageable = PageRequest.of(offset, limit);
            return productRepository.findAll(pageable).getContent();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve products: " + e.getMessage(), e);
        }
    }
}
