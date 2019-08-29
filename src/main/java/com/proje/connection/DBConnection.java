package com.proje.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBConnection {

	private static final Logger logger = LogManager.getLogger();

	private static String driver;
	private static String url;
	private static String username;
	private static String password;

	static {

		Properties properties = new Properties();

		try {
			InputStream inputStream = new FileInputStream("src/main/resources/application.properties");
			properties.load(inputStream);

			driver = properties.getProperty("db_driver");
			url = properties.getProperty("db_url");
			username = properties.getProperty("db_user");
			password = properties.getProperty("db_password");

		} catch (IOException e) {
			logger.warn("application.properties dosyasindaki veriler eslesmiyor" + e);

		}

	}

	public static Connection getConnection() {

		Connection connection = null;

		try {

			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			logger.warn("Driver bulunamadigi icin  hata meydana geldi" + e);
		}

		try {

			connection = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {
			logger.warn("Baglanti olusturuldugun hata meydana geldi" + e);
		}
		return connection;
	}
	
	public static void closeConnection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				logger.warn("ResultSet kapanirken hata olustu"+e);
			}
		}
		
		if (preparedStatement != null) {
			
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				logger.warn("preparedStatement kapanirken hata olustu"+e);

			}
		}
		
		if (connection != null) {
			
			try {
				connection.close();
			} catch (SQLException e) {
				logger.warn("connection kapanirken hata olustu"+e);

			}
		}
		
	}

}
