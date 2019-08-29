package com.proje.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.proje.connection.DBConnection;
import com.proje.model.Brand;
import com.proje.model.Category;
import com.proje.model.Product;
import com.proje.model.User;
import com.proje.model.queries.ProductQueries;
import com.proje.model.queries.UserQueries;
import com.proje.repository.ProductRepository;

public class ProductRepositoryImpl implements ProductRepository {

	private final Logger logger = LogManager.getLogger();

	private Connection connection;

	private PreparedStatement preparedStatement;

	private ResultSet resultSet;

	public Product saveProduct(Product product) {
		// saveProductQuery = "insert into product(productId, productName, unitPrice,
		// avaible, addDate, updateDate, categoryId, brandId) values(?,?,?,?,?,?,?,?)";

		connection = DBConnection.getConnection();

		try {

			LocalDateTime localDateTime = LocalDateTime.now();

			preparedStatement = (PreparedStatement) connection.prepareStatement(ProductQueries.saveProductQuery);

			preparedStatement.setInt(1, product.getProductId());
			preparedStatement.setString(2, product.getProductName());
			preparedStatement.setDouble(3, product.getUnitPrice());
			preparedStatement.setInt(4, product.getAvaible());
			preparedStatement.setTimestamp(5, Timestamp.valueOf(localDateTime));
			preparedStatement.setTimestamp(6, null);

			preparedStatement.setInt(7, product.getCategory().getCategoryId());
			preparedStatement.setInt(8, product.getBrand().getBrandId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Urun eklenirken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		return product;
	}

	public boolean SaveBatchProduct(List<Product> products) {
		connection = DBConnection.getConnection();

		try {

			LocalDateTime localDateTime = LocalDateTime.now();

			preparedStatement = (PreparedStatement) connection.prepareStatement(ProductQueries.saveProductQuery);
			for (Product product : products) {

				preparedStatement.setInt(1, product.getProductId());
				preparedStatement.setString(2, product.getProductName());
				preparedStatement.setDouble(3, product.getUnitPrice());
				preparedStatement.setInt(4, product.getAvaible());
				preparedStatement.setTimestamp(5, Timestamp.valueOf(localDateTime));
				preparedStatement.setTimestamp(6, null);

				preparedStatement.setInt(7, product.getCategory().getCategoryId());
				preparedStatement.setInt(8, product.getBrand().getBrandId());

			}

			preparedStatement.executeBatch();
		} catch (SQLException e) {
			logger.warn("Urun eklenirken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		return true;
	}

	public Product updateProduct(Product product) {
		// updateProductQuery = "update product set productName = ?, unitPrice = ?,
		// avaible = ?, updateDate = ?, categoryId = ?, brandId = ?";
		connection = DBConnection.getConnection();

		try {

			LocalDateTime localDateTime = LocalDateTime.now();

			preparedStatement = (PreparedStatement) connection.prepareStatement(ProductQueries.updateProductQuery);

			preparedStatement.setString(1, product.getProductName());
			preparedStatement.setDouble(2, product.getUnitPrice());
			preparedStatement.setInt(3, product.getAvaible());
			preparedStatement.setTimestamp(4, Timestamp.valueOf(localDateTime));
			preparedStatement.setInt(5, product.getCategory().getCategoryId());
			preparedStatement.setInt(6, product.getBrand().getBrandId());
			preparedStatement.setInt(7, product.getProductId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("Urun eklenirken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		return product;
	}

	public boolean removeProduct(int id) {
		// deleteProductQuery = "delete from product where productId = ?";
		// deleteProductQuery = "delete from product where productId = ?";
		connection = DBConnection.getConnection();

		try {

			preparedStatement = (PreparedStatement) connection.prepareStatement(ProductQueries.deleteProductQuery);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

			preparedStatement = (PreparedStatement) connection.prepareStatement(ProductQueries.deleteUser_ProductQuery);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			logger.warn("USER_PRODUCT Silinirken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, null);
		}
		return false;
	}

	public Product findProductById(int id) {
		// findProductByIdQuery = "select * from product p leftjoin category c
		// on(p.categoryId = c.categoryId) left join brand b on(p.brandId = b.brandId)
		// where productId = ?";
		connection = DBConnection.getConnection();
		Product product = null;
		try {

			preparedStatement = (PreparedStatement) connection.prepareStatement(UserQueries.findUserByIdQuery);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				int productId = resultSet.getInt("productId");
				String productName = resultSet.getString("productName");
				double unitPrice = resultSet.getDouble("unitPrice");
				int avaible = resultSet.getInt("avaible");
				Date addDate = resultSet.getDate("addDate");
				Date updateDate = resultSet.getDate("updateDate");

				int categoryId = resultSet.getInt("categoryId");
				String categoryName = resultSet.getString("categoryName");

				int brandId = resultSet.getInt("brandId");
				String brandName = resultSet.getString("brandName");

				Category category = new Category(categoryId, categoryName);
				Brand brand = new Brand(brandId, brandName);

				product = new Product(productId, productName, unitPrice, avaible, addDate, updateDate, category,
						brand);

			}

		} catch (SQLException e) {
			logger.warn("Urunler bulunurken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		return product;
	}

	public List<Product> findProducts() {
		// findProductQuery = "select * from product p left join category c on(p,categoryId = c.categoryId) left join brand on(p.brandId = b.brandId)";
		connection = DBConnection.getConnection();
		List<Product> products = new ArrayList<Product>();
		try {

			preparedStatement = (PreparedStatement) connection.prepareStatement(UserQueries.findUserQuery);
			resultSet = preparedStatement.executeQuery();
			
			
			boolean durum = true;
			while (resultSet.next()) {
					
				int productId = resultSet.getInt("productId");
				String productName = resultSet.getString("productName");
				double unitPrice = resultSet.getDouble("unitPrice");
				int avaible = resultSet.getInt("avaible");
				Date addDate = resultSet.getDate("addDate");
				Date updateDate = resultSet.getDate("updateDate");

				int categoryId = resultSet.getInt("categoryId");
				String categoryName = resultSet.getString("categoryName");

				int brandId = resultSet.getInt("brandId");
				String brandName = resultSet.getString("brandName");

				Category category = new Category(categoryId, categoryName);
				Brand brand = new Brand(brandId, brandName);

				Product product = new Product(productId, productName, unitPrice, avaible, addDate, updateDate, category,
						brand);
					products.add(product);
			}

		} catch (SQLException e) {
			logger.warn("Product listesi alinirken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		return products;
	}

}
