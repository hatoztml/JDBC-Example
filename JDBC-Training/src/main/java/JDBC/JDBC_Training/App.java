package JDBC.JDBC_Training;

import java.util.Iterator;
import java.util.List;

import com.proje.model.Brand;
import com.proje.model.Category;
import com.proje.service.BrandService;
import com.proje.service.CategoryService;
import com.proje.service.ProductService;
import com.proje.service.UserService;
import com.proje.service.impl.BrandServiceImpl;
import com.proje.service.impl.CategoryServiceImpl;
import com.proje.service.impl.ProductServiceImpl;
import com.proje.service.impl.UserServiceImpl;

public class App {
	public static void main(String[] args) {
		BrandService brandService = new BrandServiceImpl();

		CategoryService categoryService = new CategoryServiceImpl();

		ProductService productService = new ProductServiceImpl();

		UserService userService = new UserServiceImpl();

		List<Brand> brands = brandService.findBrands();

		for (Brand brand : brands) {
			System.out.println(brand.getBrandId() + " - " + brand.getBrandName());
		}
		
		List<Category> categories = categoryService.findCategories();
		for (Category category : categories) {
			System.out.println(category.getCategoryId() + " --> " + category);
		}
	}
}
