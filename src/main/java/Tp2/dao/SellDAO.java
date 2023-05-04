package Tp2.dao;

import Tp2.model.Car;
import Tp2.model.Personne;
import Tp2.model.Sell;
import jdk.jshell.spi.ExecutionControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SellDAO extends BaseDAO<Sell> {
    public SellDAO(Connection connection) {
        super(connection);
    }


    // ajout de la date :    new java.sql.Date(getDateDegree().getTime())
    @Override
    public boolean save(Sell element) throws ExecutionControl.NotImplementedException, SQLException {
        request = "INSERT INTO sell(id_personne,id_car,date) VALUES (?,?,?)";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1,element.getPersonne().getId());
        statement.setInt(2,element.getCar().getId());
        statement.setDate(3,new java.sql.Date(element.getDate().getTime()));
        int rows = statement.executeUpdate();
        resultSet = statement.getGeneratedKeys();
        if(resultSet.next()){
            element.setId(resultSet.getInt(1));
        }
        return rows ==1;
    }

    @Override
    public List<Sell> getAll() throws SQLException {
        List<Sell> sells = new ArrayList<>();
        request = "SELECT sell.id as id_sell,id_personne,id_car,date,name,year,power,price,last_name,first_name,age FROM sell INNER JOIN car ON sell.id_car = car.id INNER JOIN personne ON sell.id_personne = personne.id\n";
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            Car car = new Car(resultSet.getInt("id_car"),
                    resultSet.getString("name"),
                    resultSet.getString("year"),
                    resultSet.getFloat("power"),
                    resultSet.getFloat("price"));
            Personne personne = new Personne(resultSet.getInt("id_personne"),
                    resultSet.getString("last_name"),
                    resultSet.getString("first_name"),
                    resultSet.getInt("age"));
            Sell sell = new Sell(resultSet.getInt("id_sell"),
                    personne,car,resultSet.getDate("date"));
            sells.add(sell);
        }
        return sells;
    }

    @Override
    public boolean update(Sell element) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("sell");
    }

    @Override
    public boolean delete(int id) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("sell");
    }

    @Override
    public Sell findById(int id) throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("sell");
    }
}
