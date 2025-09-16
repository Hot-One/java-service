package sud.uz.SudDemo.category;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new RuntimeException("Error creating category: " + e.getMessage(), e);
        }
    }

    public Category getCategoryById(Long id) {
        try {
            return categoryRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving category with id " + id + ": " + e.getMessage(), e);
        }
    }

    public List<Category> getAllCategories(int offset, int limit) {
        try {
            Pageable pageable = PageRequest.of(offset, limit);
            return categoryRepository.findAll(pageable).getContent();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all categories: " + e.getMessage(), e);
        }
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        try {
            return categoryRepository.findById(id).map(category -> {
                category.setName(updatedCategory.getName());
                category.setDescription(updatedCategory.getDescription());
                category.setParent(updatedCategory.getParent());
                return categoryRepository.save(category);
            }).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error updating category with id " + id + ": " + e.getMessage(), e);
        }
    }

    public void deleteCategory(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting category with id " + id + ": " + e.getMessage(), e);
        }
    }

}
