package hotel.function;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.ResultSet;

public class KeepRecords {

	public static void reserveRoom(Connection connection, Scanner scanner) {
	    try {
	        System.out.println("\t\t*--------------------------------*");
	        System.out.print("\t\tEnter guest name: ");
	        scanner.nextLine(); 
	        String name = scanner.nextLine();

	        System.out.print("\t\tEnter room number: ");
	        int roomNumber = scanner.nextInt();
	        scanner.nextLine();

	        System.out.print("\t\tEnter contact number: ");
	        String contactNumber = scanner.nextLine();
	        System.out.println("\t\t*--------------------------------*");

	        String sql = "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES (?, ?, ?)";

	        try (PreparedStatement ps = connection.prepareStatement(sql)) {
	            ps.setString(1, name);
	            ps.setInt(2, roomNumber);
	            ps.setString(3, contactNumber);

	            int affectedRows = ps.executeUpdate(); 

	            if (affectedRows > 0) {
	                System.out.println("\t\tReservation successful!");
	            } else {
	                System.out.println("\t\tReservation failed.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

    public static void viewReservations(Connection connection) throws SQLException {
        String sql = "SELECT * FROM reservations";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery(sql)) {

            System.out.println("\t\tCurrent Reservations:");
            System.out.println("\t\t+----------------+------------------+--------------+-----------------+-----------------------+");
            System.out.println("\t\t| RESERVATION ID |       NAME       | ROOM NUMBER  | CONTACT NUMBER  |    RESERVATION DATE   |");
            System.out.println("\t\t+----------------+------------------+--------------+-----------------+-----------------------+");

            while (rs.next()) {
                int reservationsId = rs.getInt("reservation_id");
                String guestName = rs.getString("guest_name");
                int roomNumber = rs.getInt("room_number");
                String contactNumber = rs.getString("contact_number");
                String reservationDate = rs.getTimestamp("reservation_date").toString();

                System.out.printf("\t\t|%-15d | %-16s | %-12d | %-15s | %-19s |\n",
                        reservationsId, guestName, roomNumber, contactNumber, reservationDate);
            }

            System.out.println("\t\t+----------------+------------------+--------------+-----------------+-----------------------+");
        }
    }
    
    public static void getEachReservation(Connection connection, Scanner scanner) {
        try {
            System.out.print("\t\tEnter reservation ID: ");
            int reservationId = scanner.nextInt();

            if (!reservationExists(connection, reservationId)) {
                System.out.println("\t\tReservation not found for the given ID.");
                return;
            }

            String sql = "SELECT * FROM reservations WHERE reservation_id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, reservationId);

                try (ResultSet rs = ps.executeQuery()) {
                    System.out.println("\t\t Details of Reservation:");
                    System.out.println("\t\t+----------------+------------------+--------------+-----------------+-----------------------+");
                    System.out.println("\t\t| RESERVATION ID |       NAME       | ROOM NUMBER  | CONTACT NUMBER  |    RESERVATION DATE   |");
                    System.out.println("\t\t+----------------+------------------+--------------+-----------------+-----------------------+");

                    while (rs.next()) {
                        int resId = rs.getInt("reservation_id");
                        String guestName = rs.getString("guest_name");
                        int roomNumber = rs.getInt("room_number");
                        String contactNumber = rs.getString("contact_number");
                        String reservationDate = rs.getTimestamp("reservation_date").toString();

                        System.out.printf("\t\t| %-14d | %-16s | %-12d | %-15s | %-19s |\n",
                                resId, guestName, roomNumber, contactNumber, reservationDate);
                    }

                    System.out.println("\t\t+----------------+------------------+--------------+-----------------+-----------------------+");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    public static void updateReservation(Connection connection, Scanner scanner) {

        try {
            System.out.print("\t\tEnter reservation ID to update: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine(); 

            if (!reservationExists(connection, reservationId)) {
                System.out.println("\t\tReservation not found for the given ID.");
                return;
            }

            System.out.print("\t\tEnter new guest name: ");
            String newGuestName = scanner.nextLine();
            System.out.print("\t\tEnter new room number: ");
            int newRoomNumber = scanner.nextInt();
            scanner.nextLine();
            System.out.print("\t\tEnter new contact number: ");
            String newContactNumber = scanner.nextLine();

            String sql = "UPDATE reservations SET guest_name = ?, room_number = ?, contact_number = ? WHERE reservation_id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, newGuestName);
                ps.setInt(2, newRoomNumber);
                ps.setString(3, newContactNumber);
                ps.setInt(4, reservationId);

                int affectedRows = ps.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("\t\tReservation updated successfully!");
                } else {
                    System.out.println("\t\tReservation update failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public static void deleteReservation(Connection connection, Scanner scanner) {
        try {
            System.out.print("\t\tEnter reservation ID to delete: ");
            int reservationId = scanner.nextInt();

            if (!reservationExists(connection, reservationId)) {
                System.out.println("\t\tReservation not found for the given ID.");
                return;
            }

            String sql = "DELETE FROM reservations WHERE reservation_id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, reservationId);

                int affectedRows = ps.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("\t\tReservation deleted successfully!");
                } else {
                    System.out.println("\t\tReservation deletion failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public static boolean reservationExists(Connection connection, int reservationId) {
        String sql = "SELECT reservation_id FROM reservations WHERE reservation_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, reservationId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

 
    public static void exit() throws InterruptedException{
    	System.out.print("\t\tExiting System");
    	int i = 5;
    	while(i!=0) {
    		System.out.print(".");
    		System.out.flush();
    		Thread.sleep(450);
    		i--;
    	}
    	System.out.println();
    	System.out.println("\t\tThank you for using Hotel Reservation System!!!");
    }
    
}


