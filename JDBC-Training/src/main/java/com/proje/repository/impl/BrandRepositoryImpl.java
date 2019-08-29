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
import com.proje.model.queries.BrandQueries;
import com.proje.model.queries.UserQueries;
import com.proje.repository.BrandRepository;

public class BrandRepositoryImpl implements BrandRepository {

	private final Logger logger = LogManager.getLogger();

	private Connection connection;

	private PreparedStatement preparedStatement;

	private ResultSet resultSet;

	public Brand saveBrand(int id) {

		connection = DBConnection.getConnection();
		Brand brand = null;
		try {

			preparedStatement = (PreparedStatement) connection.prepareStatement(BrandQueries.findBrandByIdQuery);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				int brandId = resultSet.getInt("brandId");
				String brandName = resultSet.getString("brandName");

				brand = new Brand(brandId, brandName);

			}

		} catch (SQLException e) {
			logger.warn("Urunler bulunurken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		return brand;
	}

	public List<Brand> findBrands() {

		connection = DBConnection.getConnection();

		List<Brand> brands = new ArrayList<Brand>();
		try {

			preparedStatement = (PreparedStatement) connection.prepareStatement(BrandQueries.findBrandQuery);
			resultSet = preparedStatement.executeQuery();

			boolean durum = true;
			while (resultSet.next()) {

				int brandId = resultSet.getInt("brandId");
				String brandName = resultSet.getString("brandName");

				Brand brand = new Brand(brandId, brandName);

				brands.add(brand);
			}

		} catch (SQLException e) {
			logger.warn("Product listesi alinirken hata olustu" + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		return brands;
	}

}
