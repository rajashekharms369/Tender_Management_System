package TMS_main_folder.srv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import TMS_main_folder.Bean_classes.*;
import TMS_main_folder.Dao_files.*;
import TMS_main_folder.utilities.*;

public class Admin extends User {

	public Admin() {
		super();
	}

	public Admin(String username, String password) {
		super(username, password);
	}

	public boolean loginAdmin() {

		boolean loginStatus = false;

		Connection conn = DBUtil.provideConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("select * from admin where username = ? AND password = ? ");

			ps.setString(1, this.getUsername());
			ps.setString(2, this.getPassword());

			rs = ps.executeQuery();

			if (rs.next()) {
				System.out.println("============================================");
				System.out.println("Login Successful");
				System.out.println("============================================");
				loginStatus = true;
			} else {
				System.out.println("============================================");
				System.out.println("Login Denied! Invalid user Details");
				System.out.println("============================================");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(ps);
			DBUtil.closeConnection(rs);
			DBUtil.closeConnection(conn);
		}

		return loginStatus;
	}

	public void registerVendor() {

		Scanner sc = new Scanner(System.in);

		try {
			System.out.println("Enter Vendor userId/username");
			String username = sc.nextLine();

			System.out.println("Enter Vendor Password");
			String password = sc.nextLine();

			String status = new VendorDaoImpl().registerVendor(new Vendor(username, password));

			System.out.println("============================================");
			System.out.println(status);
			System.out.println("============================================");

		} catch (InputMismatchException e) {
			System.out.println("Invalid Input");
		}

	}

	public void viewAllVendors() {

		List<Vendor> vendors = new VendorDaoImpl().getAllVendors();

		if (vendors.isEmpty()) {
			System.out.println("=========================");
			System.out.println("No Vendors Found");
			System.out.println("=========================");
		} else {

			System.out.println("=================================");
			int count = 1;
			for (int i = 0; i < vendors.size(); i++) {
				Vendor v = vendors.get(i);
				System.out.println(count + " Vendor Details:");
				System.out.println("*ID: " + v.getVendor_id());
				System.out.println("*Name: " + v.getVendor_name());
				System.out.println("*Email: " + v.getVendor_email());
				System.out.println("*Mobile: " + v.getVendor_mobno());
				System.out.println("*Address: " + v.getAddress());
				System.out.println("*Company: " + v.getCompany());
				System.out.println("=================================");

				count++;
			}
		}

	}

	public void viewAllTendors() {

		List<Tender> tenders = new TenderDaoImpl().getAllTenders();

		if (tenders.isEmpty()) {
			System.out.println("============================================");
			System.out.println("No Tenders Found");
			System.out.println("============================================");
		} else {

			System.out.println("============================================");
			int count = 1;
			for (int i = 0; i < tenders.size(); i++) {
				Tender t = tenders.get(i);
				System.out.println(count + " Tender Details:");
				System.out.println("*ID: " + t.getTender_id());
				System.out.println("*Title: " + t.getTender_name());
				System.out.println("*Price: " + t.getTender_price());
				System.out.println("*Type: " + t.getTender_type());
				System.out.println("*Description: " + t.getTender_description());
				System.out.println("*Status: " + t.getTender_status());
				System.out.println("=================================");

				count++;
			}
		}

	}

	public void createNewTender() {

		Scanner sc = new Scanner(System.in);

		try {
			System.out.println("Enter The Tender Details");

			Tender tender = new Tender();

			System.out.println("Enter Tender Title:");
			tender.setTender_name(sc.nextLine());

			System.out.println("Enter Tender Type: {new/old/additonal}");
			tender.setTender_type(sc.nextLine());

			System.out.println("Enter Tender Price:");
			tender.setTender_price(Integer.parseInt(sc.nextLine()));

			System.out.println("Enter Tender Description:");
			tender.setTender_description(sc.nextLine());

			tender.setTender_status("Not Assigned");

			String status = new TenderDaoImpl().createTenders(tender);

			System.out.println("============================================");
			System.out.println(status);
			System.out.println("============================================");
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input");
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input");
		}

	}

	public void getAllAssignedTenders() {

		List<Tender> tenders = new TenderDaoImpl().getAllAssignedTenders();

		if (tenders.isEmpty()) {
			System.out.println("============================================");
			System.out.println("No Tenders Found which are Assigned");
			System.out.println("============================================");
		} else {

			System.out.println("============================================");
			int count = 1;
			for (int i = 0; i < tenders.size(); i++) {
				Tender t = tenders.get(i);
				System.out.println(count + " Tender Details:");
				System.out.println("*ID: " + t.getTender_id());
				System.out.println("*Title: " + t.getTender_name());
				System.out.println("*Price: " + t.getTender_price());
				System.out.println("*Type: " + t.getTender_type());
				System.out.println("*Description: " + t.getTender_description());
				System.out.println("*Status: " + t.getTender_status());
				System.out.println("=================================");

				count++;
			}
		}
	}

	public void getAllBidsOfaTender() {

		Scanner sc = new Scanner(System.in);

		try {
			System.out.println("Enter Tender Id");
			int tid = Integer.parseInt(sc.nextLine());

			List<Bidder> bids = new BidderDaoImpl().getAllBidsOfaTender(tid);

			if (bids.isEmpty()) {
				System.out.println("============================================");
				System.out.println("No Bids Found");
				System.out.println("============================================");
			} else {
				int count = 1;
				for (int i = 0; i < bids.size(); i++) {
					Bidder b = bids.get(i);
					System.out.println(count + " Bids Details:");
					System.out.println("*Bid ID: " + b.getBidder_id());
					System.out.println("*Tender ID: " + b.getTender_id());
					System.out.println("*Vendor ID: " + b.getVendor_id());
					System.out.println("*Bid Amount: " + b.getBid_amount());
					System.out.println("*Bid Status: " + b.getStatus());
					System.out.println("=================================");

					count++;
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input");
		}

	}

	public void assignTender() {

		Scanner sc = new Scanner(System.in);

		try {
			System.out.println("Enter Tender Id");
			int tid = Integer.parseInt(sc.nextLine());

			BidderDao bdao = new BidderDaoImpl();

			if (bdao.getAllBidsOfaTender(tid).isEmpty()) {
				System.out.println("============================================");
				System.out.println("No Bids Filed for tender Id: " + tid);
				System.out.println("============================================");
			} else {
				String status = bdao.acceptBid(tid);
				bdao.rejectBid(tid);

				System.out.println("===============================");
				System.out.println(status);
				System.out.println("===============================");
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input");
		}

	}

//	public void removeTender() {
//		
//		Scanner sc = new Scanner(System.in);
//
//		System.out.println("Enter Tender Id");
//		int tid = Integer.parseInt(sc.nextLine());
//		
//		
//		boolean status = new TenderDaoImpl().removeTender(tid);
//		
//		if(status) {
//			System.out.println("============================================");
//			System.out.println("Tender: " +  tid + " removed succesfully");
//			System.out.println("============================================");
//		}else {
//			System.out.println("============================================");
//			System.out.println("Tender: " +  tid + " Not Found");
//			System.out.println("============================================");
//		}
//		
//	}

	public void updateTender() {

		Scanner sc = new Scanner(System.in);

		Tender tender = new Tender();

		int tid = -1;
		try {
			System.out.println("Enter Tender Id");
			tid = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}

		Tender t = new TenderDaoImpl().getTenderDataById(tid);

		if (t != null) {

			tender.setTender_id(tid);

			System.out.println("Existing Tender Details:");
			System.out.println("*ID: " + t.getTender_id());
			System.out.println("*Title: " + t.getTender_name());
			System.out.println("*Price: " + t.getTender_price());
			System.out.println("*Type: " + t.getTender_type());
			System.out.println("*Description: " + t.getTender_description());
			System.out.println("*Status: " + t.getTender_status());
			System.out.println("=================================");
		} else {
			System.out.println("============================================");
			System.out.println("No Tenders Found for Tender ID: " + tid);
			System.out.println("============================================");
			return;
		}

		try {
			System.out.println("Enter The Tender Updated Details");

			System.out.println("Enter Tender Title:");
			tender.setTender_name(sc.nextLine());

			System.out.println("Enter Tender Type: {new/old/additonal}");
			tender.setTender_type(sc.nextLine());

			System.out.println("Enter Tender Price:");
			tender.setTender_price(Integer.parseInt(sc.nextLine()));

			System.out.println("Enter Tender Description:");
			tender.setTender_description(sc.nextLine());

			System.out.println("Enter Tender Status:");
			tender.setTender_status(sc.nextLine());

			String status = new TenderDaoImpl().updateTender(tender);

			System.out.println("===============================");
			System.out.println(status);
			System.out.println("===============================");
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input");
		}

	}

	public void getTenderDataById() {

		Scanner sc = new Scanner(System.in);

		int tid = -1;
		try {
			System.out.println("Enter Tender Id");
			tid = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}

		Tender t = new TenderDaoImpl().getTenderDataById(tid);

		if (t != null) {
			System.out.println(" Tender Details:");
			System.out.println("*ID: " + t.getTender_id());
			System.out.println("*Title: " + t.getTender_name());
			System.out.println("*Price: " + t.getTender_price());
			System.out.println("*Type: " + t.getTender_type());
			System.out.println("*Description: " + t.getTender_description());
			System.out.println("*Status: " + t.getTender_status());
			System.out.println("=================================");
		} else {
			System.out.println("============================================");
			System.out.println("No Tenders Found for Tender ID: " + tid);
			System.out.println("============================================");
		}

	}
}