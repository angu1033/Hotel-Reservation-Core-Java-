package hotel;
import hotel.function.KeepRecords;
import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.SQLException;
import java.util.Scanner;

public class HotelReservationSystem {
	private static final String url="jdbc:mysql://localhost:3306/hotel_db";
	
	private static final String user = "root";
	
	private static final String password = "Engineer1033@";
	
	public static void main(String [] args) throws ClassNotFoundException, SQLException, InterruptedException  {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			Connection connection = DriverManager.getConnection(url, user, password);
			
			while(true) {
				System.out.println();
				System.out.println("\t\t*------------------------------*");
				System.out.println("\t\t|X| HOTEL RESERVATION SYSTEM |X|");
				System.out.println("\t\t*------------------------------*");
				
				Scanner scanner = new Scanner(System.in);
				System.out.println("\t\t1. RESERVE A ROOM");
				System.out.println("\t\t2. VIEW RESERVATIONS");
				System.out.println("\t\t3. GET A GUEST INFO");
				System.out.println("\t\t4. UPDATE RESERVATION");
				System.out.println("\t\t5. DELETE RESERVATION");
				System.out.println("\t\t0. EXIT");
				System.out.print("\t\tChoose an option: ");
				
				
				int choice = scanner.nextInt();
				switch(choice) {
				
				case 1:
					KeepRecords.reserveRoom(connection, scanner);
					break;
					
				case 2:
					KeepRecords.viewReservations(connection);
					break;
					
				case 3:
					KeepRecords.getEachReservation(connection,scanner);
					break;
					
				case 4:
					KeepRecords.updateReservation(connection, scanner);
					break;
				
				case 5:
					KeepRecords.deleteReservation(connection, scanner);
					break;
				
				case 0:
					KeepRecords.exit();
					scanner.close();
					return;
					
				default:
					System.out.println("\t\tInvalid choice. Try again.");
					
				}
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());			
		}catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
