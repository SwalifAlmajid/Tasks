import java.sql.*;
import java.util.Scanner;

public class InsertMultiple {

    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@//localhost:1521/XEPDB1"; 
        String user = "SYSTEM";                      
        String password = "2002";                     

        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database!");

            String sql = "INSERT INTO EPCCO_FIRST_T_INVENTORY_YEARLY_TRANSACTIONS " +
                         "(ORGANIZATION_ID, INVENTORY_ITEM_ID, YEAR, TRANSACTION_QUANTITY, TRANSACTION_UOM) " +
                         "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);

            while (true) {
                System.out.println("\nEnter 'exit' at any time to stop inserting records.");

                System.out.print("Enter ORGANIZATION_ID (number): ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) break;
                int organizationId = Integer.parseInt(input);

                System.out.print("Enter INVENTORY_ITEM_ID (number): ");
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) break;
                int inventoryItemId = Integer.parseInt(input);

                System.out.print("Enter YEAR (varchar): ");
                String year = scanner.nextLine();
                if (year.equalsIgnoreCase("exit")) break;

                System.out.print("Enter TRANSACTION_QUANTITY (number): ");
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) break;
                double quantity = Double.parseDouble(input);

                System.out.print("Enter TRANSACTION_UOM (varchar): ");
                String uom = scanner.nextLine();
                if (uom.equalsIgnoreCase("exit")) break;

                stmt.setInt(1, organizationId);
                stmt.setInt(2, inventoryItemId);
                stmt.setString(3, year);
                stmt.setDouble(4, quantity);
                stmt.setString(5, uom);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Record inserted successfully!");
                }
            }

            // Close resources
            stmt.close();
            conn.close();
            System.out.println("Disconnected from database.");

        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC driver not found. Add ojdbc jar to your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error occurred.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number input. Aborting.");
        }
    }
}
