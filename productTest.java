package Java2_lab4;
import java.sql.*;
import java.util.Scanner;
public class productTest {
    public static void all() {
        try (
                Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop","root","");
                Statement stmt = conn.createStatement();
        ){
            String strSelect = "select * from product";
            System.out.println("The SQL statement is : " + strSelect);
            ResultSet sresult = stmt.executeQuery(strSelect);
            while (sresult.next()){
                int id = sresult.getInt("id");
                String name = sresult.getString("name");
                double price = sresult.getDouble("price");
                System.out.println(id + " , " + name + " , " + price);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public static void name(){
        try (
                Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop","root","");
                Statement stmt = conn.createStatement();
        ){
            String strSelect = "select * from product ";
            Scanner input = new Scanner(System.in);
            System.out.println("Enter the product name : ");
            String name1 = input.next();
            strSelect += " where name like \'%" + name1 + "%\'";
            ResultSet sresult = stmt.executeQuery(strSelect);
            while (sresult.next()){
                int id = sresult.getInt("id");
                String name = sresult.getString("name");
                double price = sresult.getDouble("price");
                System.out.println(id + " , " + name + " , " + price);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int choice ;
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("Enter the choice : ");
            System.out.println("1. Display all");
            System.out.println("2. Search by name");
            System.out.println("3. Exit");
            choice = input.nextInt();
            switch (choice){
                case 1:
                    all();
                    break;
                case 2:
                    name();
                    break;
                case 3:
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("invalid choice ");
                    break;
            }
        }while (choice != 3);
    }
}
