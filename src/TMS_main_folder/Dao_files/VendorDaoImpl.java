package TMS_main_folder.Dao_files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import TMS_main_folder.Bean_classes.Vendor;
import TMS_main_folder.utilities.DBUtil;

public class VendorDaoImpl implements VendorDao {

	@Override
	public String registerVendor(Vendor vendor) {

		String status = "Registration Failed!!";

		PreparedStatement ps = null;
		PreparedStatement pst = null;

		try (Connection con = DBUtil.provideConnection()) {

			pst = con.prepareStatement("select * from vendor where vendor_id=? and vendor_password = ?");

			pst.setString(1, vendor.getVendor_id());
			pst.setString(2, vendor.getVendor_password());

			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				status = "Registration Declined! Vendor Id already Registered";
			} else {

				try {

					ps = con.prepareStatement("insert into vendor(vendor_id,vendor_password) values(?,?)");

					ps.setString(1, vendor.getVendor_id());
					ps.setString(2, vendor.getVendor_password());

					int k = ps.executeUpdate();

					if (k > 0) // update successful
						status = "Registration Successful. \nYour Vendor id: " + vendor.getVendor_id()
								+ "\nThanks For Registration";
				}

				catch (SQLException e) {
//					e.printStackTrace();
					status = "Error: " + e.getErrorCode() + " : " + e.getMessage();
				}
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			status = "Error: " + e.getErrorCode() + " : " + e.getMessage();
		}

		finally {

			DBUtil.closeConnection(pst);
			DBUtil.closeConnection(ps);

		}
		return status;

	}

	@Override
	public List<Vendor> getAllVendors() {

		List<Vendor> vendorList = new ArrayList<Vendor>();

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from vendor");
			rs = ps.executeQuery();

			while (rs.next()) {
				Vendor vendor = new Vendor(rs.getString("vendor_id"), rs.getString("vendor_password"), rs.getString("vendor_name"),
						rs.getString("vendor_email"), rs.getString("vendor_mobno"), rs.getString("company"), rs.getString("address"));
				vendorList.add(vendor);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Exception occurred....");
		} finally {

			DBUtil.closeConnection(rs);

			DBUtil.closeConnection(ps);
		}

		return vendorList;
	}

	@Override
	public boolean validatePassword(String vid, String password) {
		boolean flag = false;
		Connection conn = DBUtil.provideConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement("select * from vendor where vendor_id=? and vendor_password=?");

			pst.setString(1, vid);
			pst.setString(2, password);

			rs = pst.executeQuery();

			if (rs.next())
				flag = true;

		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("Exception occurred....");
			
		} finally {

			DBUtil.closeConnection(pst);

			DBUtil.closeConnection(rs);

		}

		return flag;
	}

	@Override
	public String updateProfile(Vendor vendor) {

		String status = "Account Updation Failed";

		String vendorId = vendor.getVendor_id();
		String password = vendor.getVendor_password();

		VendorDao dao = new VendorDaoImpl();

		if (!dao.validatePassword(vendorId, password)) {

			status = status + "\nYou Have Entered Wrong Password!";

			return status;
		}

		Connection con = DBUtil.provideConnection();
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("update vendor set vendor_name=?,vendor_mobno=?,vendor_email=?,company=?,address=? where vendor_id=?");

			ps.setString(1, vendor.getVendor_name());
			ps.setString(2, vendor.getVendor_mobno());
			ps.setString(3, vendor.getVendor_email());
			ps.setString(4, vendor.getCompany());
			ps.setString(5, vendor.getAddress());
			ps.setString(6, vendor.getVendor_id());

			int x = ps.executeUpdate();

			if (x > 0) {
				status = "Account Updated Successfully!";
			}

		} catch (SQLException e) {

			status = "Error: " + e.getMessage();

//			e.printStackTrace();
		} finally {

			DBUtil.closeConnection(ps);
		}

		return status;

	}

	@Override
	public String changePassword(String vendorId, String oldPassword, String newPassword) {

		String status = "Password Updation failed!";

		VendorDao dao = new VendorDaoImpl();

		if (!dao.validatePassword(vendorId, oldPassword)) {

			status = status + "You Have Entered Wrong Old Password!";

			return status;
		}

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		try {

			ps = con.prepareStatement("update vendor set password = ? where vendor_id=?");

			ps.setString(1, newPassword);
			ps.setString(2, vendorId);

			int x = ps.executeUpdate();

			if (x > 0)
				status = "Password Updated Successfully!";

		} catch (SQLException e) {

			status = status + " Error: " + e.getMessage();

//			e.printStackTrace();
		} finally {

			DBUtil.closeConnection(con);

			DBUtil.closeConnection(ps);

		}
		return status;
	}

	@Override
	public Vendor getVendorDataById(String vendorId) {

		Vendor vendor = null;
		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from vendor where vendor_id=?");

			ps.setString(1, vendorId);

			rs = ps.executeQuery();

			if (rs.next()) {
				vendor = new Vendor(rs.getString("vendor_id"), rs.getString("vendor_password"), rs.getString("vendor_name"),
						rs.getString("vendor_email"), rs.getString("vendor_mobno"), rs.getString("company"), rs.getString("address"));

			}

		} catch (SQLException e) {

			System.out.println("Exception occurred....");
//			e.printStackTrace();
		} finally {

			DBUtil.closeConnection(con);

			DBUtil.closeConnection(rs);

			DBUtil.closeConnection(ps);
		}

		return vendor;
	}

}