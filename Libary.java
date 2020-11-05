package Java2_lab4;
import java.sql.*;
import java.util.Scanner;

public class Libary {
    public static void main(String[] args) {
        System.out.println("Welcome to Library Books");
        Scanner input = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("\nEnter the activity that you want \n =====  MENU ===== :\n" +
                    "1.  View Books\n" +
                    "2.  View Orders\n" +
                    "0.  Exit");
            choice = input.nextInt();
            switch (choice){
                case 1:
                    System.out.println("You choose View Books");
                    viewBooks();
                    break;
                case 2:
                    System.out.println("You choose View Orders");
                    viewOrders();
                    break;
                case 0:
                    System.out.println("You choose Exit! \n Bai !");
                    break;
                default:
                    System.out.println("Invalid choice ");
                    break;
            }
        }while (choice != 0);
    }
    public static void top3NewBook(){
        try (Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/libary","root","");
             Statement stmt = conn.createStatement();) {
            String strSelect = "SELECT * FROM `books` ORDER BY YearPushlish DESC LIMIT 3";
            ResultSet sresult = stmt.executeQuery(strSelect);
            System.out.println("\n IdBook \t\t  NameBook \t\t Author \t\t YearPushlish \t\t Category \t\t Price \t\t Qty \t\t Status  ");

            while (sresult.next()){
                int idBook = sresult.getInt("IdBook");
                String nameBook = sresult.getString("NameBook");
                String author = sresult.getString("Author");
                int yearPushlish = sresult.getInt("YearPushlish");
                String category = sresult.getString("Category");
                double price = sresult.getDouble("Price");
                int qty = sresult.getInt("Qty");
                int status = sresult.getInt("Status");
                System.out.println("\n" + idBook + "\t\t\t" + nameBook + "\t\t\t" + author + "\t\t\t" + yearPushlish + "\t\t\t" + category + "\t\t\t" + price + "\t\t\t" + qty + "\t\t\t" + status + "\n");
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public static void top3BestSale(){
        try (Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/libary","root","");
             Statement stmt = conn.createStatement();
        ){
            String strSelectTop3BestSale ="SELECT books.IdBook, NameBook, Category, books.Price, SUM(orderdetail.Qty) AS TotalQty FROM books INNER JOIN orderdetail ON books.IdBook = orderdetail.IdBook INNER JOIN orders ON orderdetail.IdOrder = orders.IdOrders WHERE orders.Status != 0 GROUP BY books.IdBook, NameBook, Category, books.Price ORDER BY TotalQty DESC LIMIT 3";
            ResultSet sresult = stmt.executeQuery(strSelectTop3BestSale);
            System.out.println("\n IdBook \t\t NameBook \t\t Category \t\t Price  \t\t Qty");

            while (sresult.next()){
                int idBook = sresult.getInt("books.IdBook");
                String nameBook = sresult.getString("NameBook");
                String category = sresult.getString("Category");
                Double price = sresult.getDouble("books.Price");
                int qty = sresult.getInt("TotalQty");
                System.out.println("\n" + idBook + "\t\t\t" + nameBook + "\t\t\t" + category + "\t\t\t" + price + "\t\t\t" +qty + "\n");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public static void searchByCategory(){
        try (
                Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/libary","root","");
                Statement stmt = conn.createStatement();
        ) {
            String categorySearch = "";
            Scanner input = new Scanner(System.in);
            System.out.println("\n Enter the kind of category that you want to search : ");
            categorySearch = input.nextLine();
            String strSelect = "SELECT IdBook, NameBook, Category FROM books WHERE Category LIKE \'%" + categorySearch + "%\'" ;
            ResultSet sresult = stmt.executeQuery(strSelect);
            if ( !sresult.next()){
                System.out.println("No search found!!! " );
                System.out.println("Try again later!!! ");
            }
            else {
                System.out.println("IdBook \t\t NameBook \t\t Category");

                while (sresult.next()){
                    int id = sresult.getInt("IdBook");
                    String name = sresult.getString("NameBook");
                    String category = sresult.getString("Category");
                    System.out.println(id + "\t\t\t" + name + "\t\t\t" + category);
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public static void searchByAuthor(){
        try (
                Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/libary","root","");
                Statement stmt = conn.createStatement();
        ) {
            String categorySearch = "";
            Scanner input = new Scanner(System.in);
            System.out.println("\n Enter the author's name that you want to search : ");
            categorySearch = input.nextLine();
            String strSelect = "SELECT IdBook, NameBook,Author, Category FROM books WHERE Author LIKE \'%" + categorySearch + "%\'" ;
            ResultSet sresult = stmt.executeQuery(strSelect);
            if ( !sresult.next()){
                System.out.println("No search found!!! " );
                System.out.println("Try again later!!! ");
            }
            else {
                System.out.println("\nIdBook \t\t NameBook \t\t Author \t\t Category");
                while (sresult.next()){
                    int id = sresult.getInt("IdBook");
                    String name = sresult.getString("NameBook");
                    String author = sresult.getString("Author");
                    String category = sresult.getString("Category");
                    System.out.println(id +"\t\t\t" + name + "\t\t\t" + author + "\t\t\t" + category);
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public static void searchByIdBook(){
        try (
                Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/libary","root","");
                Statement stmt = conn.createStatement();
        ) {
            int idSearch = 0 ;
            Scanner input = new Scanner(System.in);
            System.out.println("Enter the ID Book that you want to search: ");
            idSearch = input.nextInt();
            String strSelect = "SELECT IdBook, NameBook, Author, Category, Price, Qty, YearPushlish  FROM books WHERE IdBook = " +  idSearch ;
            ResultSet sresult = stmt.executeQuery(strSelect);
            System.out.println("IdBook \t\t NameBook \t\t Author \t\t Category \t\t Price \t\t Qty \t\t YearPushLish");

            while (sresult.next()){
                int id = sresult.getInt("IdBook");
                String name = sresult.getString("NameBook");
                String author = sresult.getString("Author");
                String category = sresult.getString("Category");
                double price = sresult.getDouble("Price");
                int qty = sresult.getInt("Qty");
                String year = sresult.getString("YearPushlish");
                System.out.println(id + "\t\t\t" + name + "\t\t\t" + author + "\t\t\t" + category +"\t\t\t" + price + "\t\t\t" + qty + "\t\t\t" + year);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public static void displayOrdersByStatus(int stt){
        try (Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/libary","root","");
             Statement stmt = conn.createStatement();){
            String strSelect = " ";
            String strDisplay = " ";
            switch (stt){
                case 0:
                    strSelect = "SELECT * FROM orders WHERE Status = 0";
                    strDisplay = "Các đơn hàng bị khách hủy (Status = 0) : ";
                    break;
                case 1:
                    strSelect = "SELECT * FROM orders WHERE Status = 1";
                    strDisplay = "Các đơn hàng mới tiếp nhận (Status = 1) :";
                    break;
                case 2:
                    strSelect = "SELECT * FROM orders WHERE Status = 2";
                    strDisplay = "Các đơn hàng đang chờ xử lý (Status = 2) :";
                    break;
                case 3:
                    strSelect = "SELECT * FROM orders WHERE Status = 3";
                    strDisplay = "Các đơn hàng đã đóng gói (Status = 3) :";
                    break;
                case 4:
                    strSelect = "SELECT * FROM orders WHERE Status = 4";
                    strDisplay = "Các đơn hàng đã gửi vận chuyển (Status = 4) :";
                    break;
                case 5:
                    strSelect = "SELECT * FROM orders WHERE Status = 5";
                    strDisplay = "Các đơn hàng đã giao thành công (Status =5) :";
                    break;
                default:
                    System.out.println("Invalid choice!!!");
                    break;
            }
            ResultSet sresult = stmt.executeQuery(strSelect);
            System.out.println(strDisplay);
            System.out.println("\n IdOrders \t\t IdCustomer \t\t Status \t\t Total \t\t TimeOrder");

            while (sresult.next()){
                String idOrders = sresult.getString("IdOrders");
                String idCus = sresult.getString("IdCus");
                int status = sresult.getInt("Status");
                double total = sresult.getDouble("Total");
                String timeOrder = sresult.getString("TimeOrder");
                System.out.println(idOrders + "\t\t\t" + idCus + "\t\t\t" + status + "\t\t\t" + total + "\t\t\t" + timeOrder);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public static void displayOrdersByIdCustomer(){
        try (Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/libary","root","");
             Statement stmt = conn.createStatement();){
            Scanner input = new Scanner(System.in);
            int idSearch =0;
            System.out.println("Enter the ID Customer : ");
            idSearch = input.nextInt();
            String strSelect = "SELECT * FROM orders WHERE IdCus =" + idSearch;
            System.out.println("Orders List with ID Customer = " + idSearch + " :" );
            ResultSet sresult = stmt.executeQuery(strSelect);
            System.out.println("\n IdOrders \t\t IdCustomer \t\t Status \t\t Total \t\t TimeOrder");

            while (sresult.next()){
                String idOrders = sresult.getString("IdOrders");
                String idCus = sresult.getString("IdCus");
                int status = sresult.getInt("Status");
                double total = sresult.getDouble("Total");
                String timeOrder = sresult.getString("TimeOrder");
                System.out.println(idOrders + "\t\t\t" + idCus + "\t\t\t" + status + "\t\t\t" + total + "\t\t\t" + timeOrder);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public static void displayStatusByIdOrder(){
        try (Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/libary","root","");
             Statement stmt = conn.createStatement();) {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter ID Orders : ");
            String idOrderSearch = input.nextLine();
            String strSelect = "SELECT IdOrders, Status FROM orders WHERE IdOrders = " +"\'" + idOrderSearch + "\'";
            ResultSet sresult = stmt.executeQuery(strSelect);
            System.out.println("\n IdOrders \t\t Status");
            while (sresult.next()){
                String idOrder = sresult.getString("IdOrders");
                int status = sresult.getInt("Status");
                System.out.println(idOrder + "\t\t" + status);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public static void displayOrderByIdOrder(){
        try (Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/libary","root","");
             Statement stmt = conn.createStatement();) {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter ID Orders : ");
            String idOrderSearch = input.nextLine();
            String strSelect = "SELECT * FROM orders WHERE IdOrders = " +"\'" + idOrderSearch + "\'";
            ResultSet sresult = stmt.executeQuery(strSelect);
            System.out.println("\nIdOrder \t\t IdCustomer \t\t Status \t\t Total \t\t TimeOrder ");
            while (sresult.next()){
                String idOrder = sresult.getString("IdOrders");
                int idCus = sresult.getInt("IdCus");
                int status = sresult.getInt("Status");
                double total = sresult.getDouble("Total");
                String timeOrder = sresult.getString("TimeOrder");
                System.out.println(idOrder + "\t\t\t" + idCus + "\t\t\t" + status + "\t\t\t" + total + "\t\t\t" + timeOrder);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public static void viewBooks(){
        Scanner input = new Scanner(System.in);
        int choice =0;
        do {
            System.out.println("\nChoose a way to View Books : \n" +
                    "1. View top 3 new Books\n" +
                    "2. View top 3 best sale Book\n" +
                    "3. Search Book by Category\n" +
                    "4. Search Book by Author\n" +
                    "5. Search Book by ID Book\n" +
                    "0. Back to MENU");
            choice = input.nextInt();
            switch (choice){
                case 1:
                    System.out.println("You choose to View top new Books ");
                    top3NewBook();
                    break;
                case 2:
                    System.out.println("You choose to View top 3 best sale Books");
                    top3BestSale();
                    break;
                case 3:
                    System.out.println("You choose to Search Book by Category");
                    searchByCategory();
                    break;
                case 4:
                    System.out.println("You choose to Search Book by Author ");
                    searchByAuthor();
                    break;
                case 5:
                    System.out.println("You choose to Search Book by ID Book");
                    searchByIdBook();
                    break;
                case 0:
                    System.out.println("Back to MENU");
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        } while (choice != 0);

    }
    public static void viewOrders(){
        Scanner input = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("\nChoose a way to View Orders\n" +
                    "1. Search Orders by ID Customer\n" +
                    "2. Search Order's Status by ID Order \n" +
                    "3. Search Orders by ID Order\n" +
                    "4. Display Orders were canceled by customer (Status = 0)\n" +
                    "5. Display new Orders received (Status = 1)\n" +
                    "6. Display Orders are waited to be handled (Status = 2)\n" +
                    "7. Display Orders is packed (Status = 3)\n" +
                    "8. Display Orders have sent to ship (Status = 4)\n" +
                    "9. Display Orders were completed (Status = 5)\n" +
                    "0. Back to MENU");
            choice = input.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Search Orders by ID Customer");
                    displayOrdersByIdCustomer();
                    break;
                case 2:
                    System.out.println("Search Order's Status by ID Order");
                    displayStatusByIdOrder();
                    break;
                case 3:
                    System.out.println("Search Orders by ID Order");
                    displayOrderByIdOrder();
                    break;
                case 4:
                    System.out.println("Display Orders were canceled by customer (Status = 0)");
                    displayOrdersByStatus(0);
                    break;
                case 5:
                    System.out.println("Display new Orders received (Status = 1)");
                    displayOrdersByStatus(1);
                    break;
                case 6:
                    System.out.println("Display Orders are waited to be handled (Status = 2)");
                    displayOrdersByStatus(2);
                    break;
                case 7:
                    System.out.println("Display Orders is packed (Status = 3)");
                    displayOrdersByStatus(3);
                    break;
                case 8:
                    System.out.println("Display Orders have sent to ship (Status = 4)");
                    displayOrdersByStatus(4);
                    break;
                case 9:
                    System.out.println("Display Orders were completed (Status = 5)");
                    displayOrdersByStatus(5);
                    break;
                case 0:
                    System.out.println("Back to MENU");
                    break;
                default:
                    System.out.println("Invalid choice!!!");
                    break;
            }
        }while (choice != 0);
    }
}