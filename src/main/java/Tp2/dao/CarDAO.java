package Tp2.dao;

import Tp2.model.Car;
import Tp2.model.Personne;
import jdk.jshell.spi.ExecutionControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarDAO extends BaseDAO<Car> {

    public CarDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(Car element) throws SQLException {
        request = "INSERT INTO car(name,year,power,price) VALUE (?,?,?,?)";
        statement= _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,element.getName());
        statement.setString(2,element.getYear());
        statement.setFloat(3,element.getPower());
        statement.setFloat(4,element.getPrice());
        int rows = statement.executeUpdate();
        resultSet = statement.getGeneratedKeys();
        if(resultSet.next()){
            element.setId(resultSet.getInt(1));
        }
        return rows ==1;
    }

    @Override
    public List<Car> getAll() throws SQLException {
        List<Car> cars = new ArrayList<>();
        request = "SELECT * FROM car";
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            cars.add(new Car(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("year"),
                    resultSet.getFloat("power"),
                    resultSet.getFloat("price") ));
        }
        return cars;
    }

    @Override
    public boolean update(Car element) throws SQLException {
        request = "UPDATE car SET name = ?, year = ?,power = ?,price = ? WHERE id =?";
        statement = _connection.prepareStatement(request);
        statement.setString(1,element.getName());
        statement.setString(2,element.getYear());
        statement.setFloat(3,element.getPower());
        statement.setFloat(4,element.getPrice());
        statement.setInt(5,element.getId());
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        request = "DELETE FROM car WHERE id =?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1,id);
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    @Override
    public Car findById(int id) throws SQLException {
        Car car = null;
        request = "SELECT * FROM car WHERE id = ?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1,id);
        resultSet = statement.executeQuery();
        if(resultSet.next()){
            car = new Car(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("year"),
                    resultSet.getFloat("power"),
                    resultSet.getFloat("price"));
        }
        return car;
    }
}
