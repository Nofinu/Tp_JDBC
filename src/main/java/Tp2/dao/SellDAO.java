package Tp2.dao;

import Tp2.model.Sell;
import jdk.jshell.spi.ExecutionControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
    public List<Sell> getAll() throws ExecutionControl.NotImplementedException, SQLException {
        return null;
    }

    @Override
    public boolean update(Sell element) throws ExecutionControl.NotImplementedException, SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws ExecutionControl.NotImplementedException, SQLException {
        return false;
    }

    @Override
    public Sell findById(int id) throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("sell");
    }
}
