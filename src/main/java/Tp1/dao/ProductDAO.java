package Tp1.dao;

import Tp1.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private Connection _connection;
    private PreparedStatement statement;
    private String request;
    private ResultSet resultSet;

    public ProductDAO(Connection connection) {
        _connection = connection;
    }

    public boolean save(Product product) throws SQLException {
        request = "INSERT INTO product(name,price,quantity,description) VALUES (?,?,?,?)";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,product.getName());
        statement.setDouble(2,product.getPrice());
        statement.setInt(3,product.getQuantity());
        statement.setString(4,product.getDescription());
        int rows = statement.executeUpdate();
        resultSet = statement.getGeneratedKeys();
        if(resultSet.next()){
            product.setId(1);
        }
        return rows == 1;
    }

    public boolean edit(Product product) throws SQLException {
        request ="UPDATE product SET name = ?, price = ?,quantity = ?, description = ? WHERE id = ?";
        statement = _connection.prepareStatement(request);
        statement.setString(1,product.getName());
        statement.setDouble(2,product.getPrice());
        statement.setInt(3,product.getQuantity());
        statement.setString(4,product.getDescription());
        statement.setInt(5,product.getId());
        int rows = statement.executeUpdate();
        return rows == 1;
    }

    public boolean delete(int id) throws SQLException {
        request ="DELETE FROM product WHERE id = ?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1,id);
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    public List<Product> findAll () throws SQLException{
        List<Product> products = new ArrayList<>();
        request = "SELECT * FROM product";
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            products.add(new Product(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("quantity"),
                    resultSet.getString("description") ));

        }
        return products;
    }

    public Product findById (int id) throws SQLException{
        Product product = null;
        request = "SELECT * FROM product WHERE id =?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1,id);
        resultSet = statement.executeQuery();
        if(resultSet.next()){
            product = new Product(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("quantity"),
                    resultSet.getString("description"));
        }
        return product;
    }
}
