package Tp1;

import Tp1.dao.ProductDAO;
import Tp1.model.Product;
import org.example.utile.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IHM {
    private Scanner scanner;

    private ProductDAO productDAO;
    private Connection connection;

    public IHM() {
        this.scanner = new Scanner(System.in);
    }

    public void start (){
        int entry;
        do{
            menu();
            entry = scanner.nextInt();
            scanner.nextLine();

            switch (entry){
                case 1:
                    addProductAction();
                    break;
                case 2:
                    showAllAction();
                    break;
                case 3:
                    showById();
                    break;
                case 4:
                    updateProductAction();
                    break;
                case 5:
                    deletProductAction();
                    break;
                default:
                    System.out.println("entrer une valeur valide ");
                    break;
            }
        }while(entry !=6);
    }

    private void menu (){
        System.out.println("------- menu -------");
        System.out.println("1-- enregistrer un produit");
        System.out.println("2-- afficher la liste des produit");
        System.out.println("3-- afficher un produit par sont id");
        System.out.println("4-- mise a jour d'un produit");
        System.out.println("5-- supprimer un produit");
        System.out.println("0-- exit");
        System.out.println("----------------------");
    }

    private void addProductAction (){
        try{
            System.out.println("------ ajout de produit --------");
            System.out.println("entrer toute les information du produit ");
            System.out.println("nom :");
            String name = scanner.nextLine();

            System.out.println("prix :");
            Double price = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("quantité :");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            System.out.println("desciption ");
            String description = scanner.nextLine();

            Product produit = new Product(name,price,quantity,description);

            connection = new DatabaseManager().getConnection();
            connection.setAutoCommit(false);
            productDAO = new ProductDAO(connection);

            if(productDAO.save(produit)){
                System.out.println("le produit a bien ete ajouté");
                connection.commit();
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            try{
                connection.rollback();
            }catch (SQLException exp){
                System.out.println(exp.getMessage());
            }
        }
        catch (InputMismatchException e){
            System.out.println(e.getMessage());
            addProductAction();
        }
    }

    private void showAllAction (){
        try{
            productDAO = new ProductDAO(new DatabaseManager().getConnection());
            productDAO.findAll().forEach(e -> System.out.println(e));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private void showById (){
        System.out.println("------ affichage par id --------");
        System.out.println("entrer l'id du produit voulu :");
        int id = scanner.nextInt();
        scanner.nextLine();
        try{
            productDAO = new ProductDAO(new DatabaseManager().getConnection());
            Product product = productDAO.findById(id);
            if(product != null){
                System.out.println(product);
            }else{
                System.out.println("aucun produit trouver a cette id");
            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private void updateProductAction (){
        Product product =null;
        System.out.println("-------- modifier un produit ---------");
        System.out.println("entrer l'id du produit a modifier");
        int id = scanner.nextInt();
        scanner.nextLine();
        try{
            connection = new DatabaseManager().getConnection();
            connection.setAutoCommit(false);
            productDAO = new ProductDAO(connection);
            product=productDAO.findById(id);
            if(product != null){
                System.out.println("nom ["+product.getName()+"] :");
                product.setName(scanner.nextLine());

                System.out.println("prix ["+product.getPrice()+"] :");
                product.setPrice(scanner.nextDouble());
                scanner.nextLine();

                System.out.println("quantité ["+product.getQuantity()+"] :");
                product.setQuantity(scanner.nextInt());
                scanner.nextLine();

                System.out.println("description ["+product.getDescription()+"] :");
                product.setDescription(scanner.nextLine());

                if(productDAO.edit(product)){
                    System.out.println("le produit a ete modifié");
                    connection.commit();
                }
            }else {
                System.out.println("aucune produit trouver a cette id");
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
    private void deletProductAction (){
        System.out.println("------- suppresion d'un produit ------");
        System.out.println("entrer l'id du produit que vous voulez supprimer :");
        int id = scanner.nextInt();
        scanner.nextLine();
        try{
            connection = new DatabaseManager().getConnection();
            connection.setAutoCommit(false);
            productDAO = new ProductDAO(connection);
            if(productDAO.delete(id)){
                System.out.println("le produit a bien ete suprimer");
                connection.commit();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            try{
                connection.rollback();
            }catch(SQLException exp){
                System.out.println(exp.getMessage());
            }
        }
    }
}
