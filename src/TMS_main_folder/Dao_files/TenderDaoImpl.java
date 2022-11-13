package TMS_main_folder.Dao_files;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import TMS_main_folder.Bean_classes.*;
import TMS_main_folder.utilities.*;

public class TenderDaoImpl implements TenderDao {

	@Override
	public List<Tender> getAllTenders() {

		List<Tender> tenderList = new ArrayList<Tender>();

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from tender");

			rs = ps.executeQuery();
			while (rs.next()) {
				Tender tender = new Tender();

				tender.setTender_id(rs.getInt("tender_id"));
				tender.setTender_name(rs.getString("tender_name"));
				tender.setTender_type(rs.getString("tender_type"));
				tender.setTender_price(rs.getInt("tender_price"));
				tender.setTender_description(rs.getString("tender_description"));
				tender.setTender_status(rs.getString("tender_status"));
				tenderList.add(tender);
			}

		} catch (SQLException e) {

			System.out.println("Exception occurred....");
//			e.printStackTrace();
		} finally {

			DBUtil.closeConnection(ps);

			DBUtil.closeConnection(rs);

			DBUtil.closeConnection(con);

		}

		return tenderList;
	}

	@Override
	public String createTender(Tender tender) {

		String status = "Tender Addition Failed!";

		Connection conn = DBUtil.provideConnection();

		PreparedStatement pst = null;
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("select * from tender where tender_name=? AND tender_type=? AND tender_price=? AND tender_description=?");

			ps.setString(1, tender.getTender_name());
			ps.setString(2, tender.getTender_type());
			ps.setInt(3, tender.getTender_price());
			ps.setString(4, tender.getTender_description());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				status = "Tender Declined! \nTender already Exists with ID: " + rs.getInt("tender_id");
			} else {
				try {
					pst = conn.prepareStatement("insert into tender(tender_name,tender_type,tender_price,tender_description,tender_status) values(?,?,?,?,?)");
	
					pst.setString(1, tender.getTender_name());
					pst.setString(2, tender.getTender_type());
					pst.setInt(3, tender.getTender_price());
					pst.setString(4, tender.getTender_description());
					pst.setString(5, tender.getTender_status());

					int x = pst.executeUpdate();
					if (x > 0)
						status = "New Tender Inserted \nYour Tender ID: " + getTenderId(tender);
				} catch (SQLException e) {

					status = "Error : " + e.getMessage();

//					e.printStackTrace();
				}
			}

		} catch (SQLException e) {

			status = "Error : " + e.getMessage();

//			e.printStackTrace();

		} finally {

			DBUtil.closeConnection(pst);

			DBUtil.closeConnection(ps);

			DBUtil.closeConnection(conn);

		}

		return status;
	}
	
	@Override
	public int getTenderId(Tender tender) {

		int tid = -1;

		Connection conn = DBUtil.provideConnection();

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("select * from tender where tender_name=? AND tender_type=? AND tender_price=? AND tender_description=?");

			ps.setString(1, tender.getTender_name());
			ps.setString(2, tender.getTender_type());
			ps.setInt(3, tender.getTender_price());
			ps.setString(4, tender.getTender_description());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				tid = rs.getInt("tender_id");
			} 

		} catch (SQLException e) {

			System.out.println("Exception occurred....");
//			e.printStackTrace();

		} finally {

			DBUtil.closeConnection(ps);

			DBUtil.closeConnection(conn);

		}

		return tid;
	}

//	@Override
//	public boolean removeTender(int tid) {
//
//		boolean flag = false;
//
//		Connection con = DBUtil.provideConnection();
//
//		PreparedStatement ps = null;
//		
//		try {
//
//			ps = con.prepareStatement("delete from tender where tid=?");
//
//			ps.setInt(1, tid);
//
//			int x = ps.executeUpdate();
//
//			if (x > 0) {
//
//				flag = true;
//
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//
//			DBUtil.closeConnection(ps);
//
//			DBUtil.closeConnection(con);
//
//		}
//
//		return flag;
//	}

	@Override
	public String updateTender(Tender tender) {

		String status = "Tender Updation Failed!";

		Connection con = DBUtil.provideConnection();

		PreparedStatement pst = null;

		try {
			pst = con.prepareStatement(
					"UPDATE tender SET tender_name=?,tendor_type=?,tender_price=?,tender_description=?, tender_status=? where tender_id=?");

			pst.setString(1, tender.getTender_name());
			pst.setString(2, tender.getTender_type());
			pst.setInt(3, tender.getTender_price());
			pst.setString(4, tender.getTender_description());
			pst.setString(5, tender.getTender_status());
			
			pst.setInt(6, tender.getTender_id());
			
			int x = pst.executeUpdate();
			if (x > 0)
				status = "TENDER DETAILS UPDATED SUCCSESFULLY";

		} catch (SQLException e) {
			status = "Error: " + e.getMessage();
//			e.printStackTrace();
		} finally {

			DBUtil.closeConnection(pst);

			DBUtil.closeConnection(con);

		}

		return status;
	}

	@Override
	public Tender getTenderDataById(int tid) {

		Tender tender = null;

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		try {

			ps = con.prepareStatement("select * from tender where tender_id=?");

			ps.setInt(1, tid);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String type = rs.getString(3);
				int price = rs.getInt(4);
				String desc = rs.getString(5);
				String status = rs.getString(6);

				tender = new Tender(id, name, type, price, desc, status);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();

			System.out.println("Exception occurred....");
		} finally {

			DBUtil.closeConnection(ps);

			DBUtil.closeConnection(con);

		}

		return tender;
	}

	@Override
	public String getTenderStatus(int tenderId) {

		String status = "Not Assigned";

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from tender where tender_id=?");

			ps.setInt(1, tenderId);

			rs = ps.executeQuery();

			if (rs.next()) {
				status = rs.getString("tender_status");
			} else {
				status = "Tendor Id Not Found: " + tenderId;
			}

		} catch (SQLException e) {
			status = "Error: " + e.getMessage();
//			e.printStackTrace();
		} finally {

			DBUtil.closeConnection(con);
			DBUtil.closeConnection(ps);
			DBUtil.closeConnection(rs);

		}
		return status;
	}

	@Override
	public String assignTender(int tenderId) {

		String status = "Tender Assigning failed";

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("update tender set tender_status='Assigned' where tender_id=?");
			ps.setInt(1, tenderId);

			int k = ps.executeUpdate();
			if (k > 0) {
				status = "Tender: " + tenderId + " has been Assigned";
			}

		} catch (SQLException e) {
			status = status + e.getMessage();
//			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(ps);
			DBUtil.closeConnection(rs);
			DBUtil.closeConnection(con);
		}

		return status;
	}

	@Override
	public List<Tender> getAllAssignedTenders() {

		List<Tender> statusList = new ArrayList<Tender>();

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		ResultSet rs = null;

		try {

			ps = con.prepareStatement("select * from tender where tender_status='Assigned'");

			rs = ps.executeQuery();

			while (rs.next()) {

				Tender tender = new Tender();

				tender.setTender_id(rs.getInt("tender_id"));
				tender.setTender_name(rs.getString("tender_name"));
				tender.setTender_type(rs.getString("tender_type"));
				tender.setTender_price(rs.getInt("tender_price"));
				tender.setTender_description(rs.getString("tender_description"));
				tender.setTender_status(rs.getString("tender_status"));

				statusList.add(tender);
			}

		} catch (SQLException e) {

			System.out.println("Exception occurred....");
//			e.printStackTrace();
		}

		finally {

			DBUtil.closeConnection(con);

			DBUtil.closeConnection(ps);

			DBUtil.closeConnection(rs);

		}

		return statusList;
	}

}