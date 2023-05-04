package Tp2.dao;

import Tp2.model.Personne;
import jdk.jshell.spi.ExecutionControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonneDAO extends BaseDAO<Personne> {
    public PersonneDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(Personne element) throws SQLException {
        request = "INSERT INTO personne(last_name,first_name,age) VALUE (?,?,?)";
        statement= _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,element.getLastName());
        statement.setString(2,element.getFirstName());
        statement.setInt(3,element.getAge());
        int rows = statement.executeUpdate();
        resultSet = statement.getGeneratedKeys();
        if(resultSet.next()){
            element.setId(resultSet.getInt(1));
        }
        return rows ==1;
    }

    @Override
    public List<Personne> getAll() throws SQLException {
        List<Personne> personnes = new ArrayList<>();
        request = "SELECT * FROM personne";
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            personnes.add(new Personne(resultSet.getInt("id"),
                    resultSet.getString("last_name"),
                    resultSet.getString("first_name"),
                    resultSet.getInt("age")));
        }
        return personnes;
    }

    @Override
    public boolean update(Personne element) throws SQLException {
        request = "UPDATE personne SET last_name = ?, first_name = ?,age = ? WHERE id =?";
        statement = _connection.prepareStatement(request);
        statement.setString(1,element.getLastName());
        statement.setString(2,element.getFirstName());
        statement.setInt(3,element.getAge());
        statement.setInt(4,element.getId());
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        request = "DELETE FROM personne WHERE id =?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1,id);
        int rows = statement.executeUpdate();
        return rows ==1;
    }

    @Override
    public Personne findById(int id) throws SQLException {
        Personne personne = null;
        request = "SELECT * FROM personne WHERE id = ?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1,id);
        resultSet = statement.executeQuery();
        if(resultSet.next()){
            personne = new Personne(resultSet.getInt("id"),
                    resultSet.getString("last_name"),
                    resultSet.getString("first_name"),
                    resultSet.getInt("age"));
        }
        return personne;
    }
}
