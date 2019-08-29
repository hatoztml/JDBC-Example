package com.proje.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.proje.connection.DBConnection;
import com.proje.model.Brand;
import com.proje.model.Category;
import com.proje.model.Product;
import com.proje.model.queries.UserQueries;
import com.proje.repository.CAtegoryRepository;

public class CategoryRepositoryImpl implements CAtegoryRepository {

	private final Logger logger = LogManager.getLogger();

	private Connection connection;

	private PreparedStatement preparedStatement;

	private ResultSet resultSet;

	public Category findCategoryById(int id) {
		connection = DBConnection.getConnection();
		Category category = null;
		try {

			preparedStatement = (PreparedStatement) connection.prepareStatement(UserQueries.findUserByIdQuery);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				int categoryId = resultSet.getInt("categoryId");
				String categoryName = resultSet.getString("categoryName");

				category = new Category(categoryId, categoryName);

			}

		} catch (SQLException e) {
			logger.warn("Urunler bulunurken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		return category;
	}

	public List<Category> findCategories() {
		connection = DBConnection.getConnection();
		List<Category> categorys = new ArrayList<Category>();
		try {

			preparedStatement = connection.prepareStatement(UserQueries.findUserQuery);
			resultSet = preparedStatement.executeQuery();

			boolean durum = true;
			while (resultSet.next()) {

				int categoryId = resultSet.getInt("categoryId");
				String categoryName = resultSet.getString("categoryName");

				Category category = new Category(categoryId, categoryName);

				categorys.add(category);
			}

		} catch (SQLException e) {
			logger.warn("Product listesi alinirken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		return categorys;
	}

}
