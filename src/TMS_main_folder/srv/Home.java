package TMS_main_folder.srv;

import java.util.Scanner;

public class Home {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.println("===========================================================================");
		System.out.println();
		System.out.println("******************** Welcome To Tender Management System ****************");
		System.out.println();
		System.out.println("===========================================================================");

		while (true) {

			System.out.println("Do You Want to Login ? : {Y/N}");
			String choice = sc.nextLine();

			if (choice.equalsIgnoreCase("N")) {
				System.out.println("=================================");
				System.out.println("Thank You for using our System.");
				System.out.println("=================================");
				break;
			} else {
				System.out.println("Choose User Type: \n" + "1. Admin \n" + "2. Vendor");

				try {
					int key = Integer.parseInt(sc.nextLine());

					if (key < 1 || key > 2) {
						System.out.println("=================================");
						System.out.println("Invalid Choice! " + key);
						System.out.println("=================================");
					} else {
						System.out.println("Enter username");
						String uname = sc.nextLine();

						System.out.println("Enter password");
						String upass = sc.nextLine();

						if (key == 1) {
							Admin user = new Admin(uname, upass);

							if (user.loginAdmin()) {
								Home.loginAdmin(user);
							}
						} else {
							VendorUser user = new VendorUser(uname, upass);
							if (user.loginVendor()) {
								Home.vendorLogin(user);
							}
						}

					}
				} catch (NumberFormatException e) {
					System.out.println("=================================");
					System.out.println("Invalid Choice! ");
					System.out.println("=================================");
				}

			}

		}

	}

	public static void loginAdmin(Admin user) {

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("Enter Choice of Operation: \n"
					+ "1. Register new Vendor. (assign a new username and password to him) \n"
					+ "2. View all the vendors. \n" + "3. Create new tenders. \n" + "4. View All the Tenders. \n"
					+ "5. View All the Bids of a tender. \n" + "6. Assign tender to a vendor. \n"
					+ "7. View All the Assigned tenders. \n" + "8. View Tender by ID. \n" + "9. Update a Tendor. \n"
//					+ "10. Remove a Tendor. \n"
					+ "10. Logout and Exit from Admin Account.");

			try {
				int key = Integer.parseInt(sc.nextLine());

				if (key == 10) {
					System.out.println("=================================");
					System.out.println("Logout Succesful \nReturning to Home");
					System.out.println("=================================");
					return;
				}

				switch (key) {
				case 1: {
					user.registerVendor();
					break;
				}
				case 2: {
					user.viewAllVendors();
					break;
				}
				case 3: {
					user.createNewTender();
					break;
				}
				case 4: {
					user.viewAllTendors();
					;
					break;
				}
				case 5: {
					user.getAllBidsOfaTender();
					;
					break;
				}
				case 6: {
					user.assignTender();
					break;
				}
				case 7: {
					user.getAllAssignedTenders();
					break;
				}
				case 8: {
					user.getTenderDataById();
					;
					break;
				}
				case 9: {
					user.updateTender();
					break;
				}
//				case 10: {
//					user.removeTender();
//					break;
//				}
				default:
					System.out.println("=================================");
					System.out.println("Invalid Choice");
					System.out.println("=================================");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("=================================");
				System.out.println("Invalid Choice! ");
				System.out.println("=================================");
			}

		}

	}

	public static void vendorLogin(VendorUser user) {

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("Enter Choice of Operation: \n" + "1. View all the current Tenders. \n"
					+ "2. Place a Bid against a Tender. \n" + "3. View status of a Bid(Whether Selected or Not) \n"
					+ "4. View his own Bid History. \n" + "5. Update Profile. \n"
					+ "6. Logout and Exit from Vendor Account.");

			try {
				int key = Integer.parseInt(sc.nextLine());

				if (key == 6) {
					System.out.println("=================================");
					System.out.println("Logout Succesful \nReturning to Home");
					System.out.println("=================================");
					return;
				}

				switch (key) {
				case 1: {
					user.viewAllCurrentTendors();
					break;
				}
				case 2: {
					user.bidTender();
					;
					break;
				}
				case 3: {
					user.getStatusOfABid();
					break;
				}
				case 4: {
					user.getAllBidsOfaVendor();
					;
					break;
				}
				case 5: {
					user.updateProfile();
					;
					break;
				}
				default:
					System.out.println("=================================");
					System.out.println("Invalid Choice");
					System.out.println("=================================");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("=================================");
				System.out.println("Invalid Choice");
				System.out.println("=================================");
			}

		}

	}

}