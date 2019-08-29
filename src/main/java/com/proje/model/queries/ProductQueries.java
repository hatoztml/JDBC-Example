package com.proje.model.queries;

public class ProductQueries {
	
	public static final String saveProductQuery = "insert into product(productId, productName, unitPrice, avaible, addDate, updateDate, categoryId, brandId) values(?,?,?,?,?,?,?,?)";
	
	public static final String updateProductQuery = "update product set productName = ?, unitPrice = ?, avaible = ?, updateDate = ?, categoryId = ?, brandId = ?";
	
	public static final String deleteUser_ProductQuery = "delete from user_product where productId = ?";
	
	public static final String deleteProductQuery = "delete from product where productId = ?";
	
	public static final String findProductByIdQuery = "select * from product where productId = ?";
	
	public static final String findProductQuery = "select * from product";

}
