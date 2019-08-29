package com.proje.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.proje.connection.DBConnection;
import com.proje.model.Brand;
import com.proje.model.Category;
import com.proje.model.Product;
import com.proje.model.User;
import com.proje.model.queries.UserQueries;
import com.proje.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {

	private final Logger logger = LogManager.getLogger();

	private Connection connection;

	private PreparedStatement preparedStatement;

	private ResultSet resultSet;

	public User saveUser(User user) {
		// saveUserQuery = "insert into user(userId, firstName, lastName, birthOfDate,
		// username) values(?,?,?,?,?,?)";

		connection = DBConnection.getConnection();

		try {

			preparedStatement = (PreparedStatement) connection.prepareStatement(UserQueries.saveUserQuery);
			preparedStatement.setInt(1, user.getUserId());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setDate(4, user.getBirthOfDate());
			preparedStatement.setString(5, user.getUsername());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn(user.getUserId() + "id`li user kaydedilirken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, null);
		}
		return user;
	}

	public boolean saveUserProduct(int userId, int productId) {

		// saveUser_ProductQuery = "insert into user_product(userId,productId)
		// values(?,?)";

		connection = DBConnection.getConnection();

		try {

			preparedStatement = (PreparedStatement) connection.prepareStatement(UserQueries.saveUser_ProductQuery);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, productId);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("USER_PRODUCT kaydedilirken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, null);
		}
		return true;
	}

	public User updateUser(User user) {

		// updateUserQuery = "update user set firstname = ?, lastname = ?, birthOfDate =
		// ?, username = ? where userId = ?";

		connection = DBConnection.getConnection();

		try {

			preparedStatement = (PreparedStatement) connection.prepareStatement(UserQueries.updateUserQuery);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setDate(3, user.getBirthOfDate());
			preparedStatement.setString(4, user.getUsername());
			preparedStatement.setInt(5, user.getUserId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.warn("User guncellenirken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, null);
		}
		return user;
	}

	public boolean removeUser(int id) {
		// deleteUserByIdQuery = "delete from user where userId = ?";
		// deleteUser_ProductByIdQuery = "delete from user_product where userId = ?";

		connection = DBConnection.getConnection();

		try {

			preparedStatement = (PreparedStatement) connection.prepareStatement(UserQueries.deleteUserByIdQuery);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

			preparedStatement = (PreparedStatement) connection
					.prepareStatement(UserQueries.deleteUser_ProductByIdQuery);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			logger.warn("USER_PRODUCT kaydedilirken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, null);
		}
		return false;
	}

	public User findUserById(int id) {
		// findUserByIdQuery = "select * from user where userId = ?";
		connection = DBConnection.getConnection();

		User user = null;
		try {

			preparedStatement = (PreparedStatement) connection.prepareStatement(UserQueries.findUserByIdQuery);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				int userId = resultSet.getInt("userId");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				Date birthOfDate = resultSet.getDate("birthOfDate");
				String username = resultSet.getNString("username");

				user = new User(userId, firstName, lastName, birthOfDate, username);

			}

		} catch (SQLException e) {
			logger.warn("User bulunurken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		return user;
	}

	public User findUserProductById(int id) {
		// findUserProductQuery = "select * from user u left outer join user_product up
		// on(u.userId = up.userId)"
		// + "left join product p on(up.productId = p.productId)"
		// + "left join category c on(p.categoryId = c.categoryId)"
		// + "left join brand b on(p.brandId = b.brandId) where u.userId = ?";
		connection = DBConnection.getConnection();
		User user = null;
		try {

			preparedStatement = (PreparedStatement) connection.prepareStatement(UserQueries.findUserByIdQuery);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			
			List<Product> products = new ArrayList<Product>();
			boolean durum = true;
			while (resultSet.next()) {

				if (durum) {
					
					int userId = resultSet.getInt("userId");
					String firstName = resultSet.getString("firstName");
					String lastName = resultSet.getString("lastName");
					Date birthOfDate = resultSet.getDate("birthOfDate");
					String username = resultSet.getNString("username");

					user = new User(userId, firstName, lastName, birthOfDate, username);

					durum = false;
				}
				
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
				
				Category category = new Category(categoryId,categoryName);
				Brand brand = new Brand(brandId,brandName);
				
				Product product = new Product(productId, productName,unitPrice, avaible,addDate,updateDate,category,brand);
				
				products.add(product);
			}
			user.setProducts(products);

		} catch (SQLException e) {
			logger.warn("User ve Urunler bulunurken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		return user;
	}

	public List<User> findUsers() {
		connection = DBConnection.getConnection();
		List<User> users = new ArrayList<User>();
		try {

			preparedStatement = (PreparedStatement) connection.prepareStatement(UserQueries.findUserQuery);
			resultSet = preparedStatement.executeQuery();
			
			
			boolean durum = true;
			while (resultSet.next()) {
					
					int userId = resultSet.getInt("userId");
					String firstName = resultSet.getString("firstName");
					String lastName = resultSet.getString("lastName");
					Date birthOfDate = resultSet.getDate("birthOfDate");
					String username = resultSet.getNString("username");

					User user = new User(userId, firstName, lastName, birthOfDate, username);
					users.add(user);
			}

		} catch (SQLException e) {
			logger.warn("User listesi alinirken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		return users;
	}

}
