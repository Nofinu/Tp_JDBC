package Tp2;

import Tp2.dao.CarDAO;
import Tp2.dao.PersonneDAO;
import Tp2.model.Car;
import Tp2.model.Personne;
import org.example.utile.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Scanner;

public class IHM {
    private Scanner scanner;

    private Connection connection;

    public IHM (){
        scanner = new Scanner(System.in);
    }
    public void start (){
        int entry ;
        do{
            menu();
            entry = scanner.nextInt();
            scanner.nextLine();

            switch (entry){
                case 1:
                    addAction(1);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    addAction(2);
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                default:
                    System.out.println("entrer une valeur valide");
                    break;

            }

        }while(entry != 0);
    }

    private void menu (){
        System.out.println("--------- menu ---------");
        System.out.println("1-- enregistrer une voiture");
        System.out.println("2-- Afficher toute les voiture");
        System.out.println("3-- supprimer une voiture");
        System.out.println("4-- modiffier une voiture");
        System.out.println("--------------------------");
        System.out.println("5-- enregistrer une personne");
        System.out.println("6-- Afficher toute les personnes");
        System.out.println("7-- supprimer une personne");
        System.out.println("8-- modiffier une personne");
    }

    private void addAction (int type){
        Personne personne = null;
        Car car= null;

        switch (type){
            case 1:
                car = addCarMenu();
                break;
            case 2 :
                personne = addPersonneMenu();
                break;
            case 3:
                break;
        }

        try{
            connection = new DatabaseManager().getConnection();
            connection.setAutoCommit(false);
            switch (type){
                case 1:
                    CarDAO carDAO = new CarDAO(connection);
                    if(car != null && carDAO.save(car)){
                        System.out.println("la voiture a ete ajouté");
                        connection.commit();
                    }
                    break;
                case 2 :
                    PersonneDAO personneDAO = new PersonneDAO(connection);
                    if(personne != null && personneDAO.save(personne)){
                        System.out.println("la personne a bien ete ajouté");
                        connection.commit();
                    }
                    break;
                case 3:
                    break;
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            try{
                connection.rollback();
            }catch (SQLException exp){
                System.out.println(exp.getMessage());
            }
        }
    }
    private Car addCarMenu(){
        System.out.println("-------- ajout d'une voiture -------");
        System.out.println("nom :");
        String name = scanner.nextLine();
        System.out.println("year :");
        String year = scanner.nextLine();
        System.out.println("puissance :");
        float power = scanner.nextFloat();
        System.out.println("prix :");
        float price = scanner.nextFloat();
        scanner.nextLine();

        Car car = new Car(name,year,power,price);
        return car;
    }
    private Personne addPersonneMenu(){
        System.out.println("-------- ajout d'une personne -------");
        System.out.println("nom :");
        String lastName = scanner.nextLine();
        System.out.println("prenom :");
        String firstName = scanner.nextLine();
        System.out.println("age :");
        int age = scanner.nextInt();
        scanner.nextLine();

        Personne personne = new Personne(lastName,firstName,age);
        return personne;
    }
}
