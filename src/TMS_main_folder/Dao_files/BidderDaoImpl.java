package TMS_main_folder.Dao_files;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import TMS_main_folder.Bean_classes.*;
import TMS_main_folder.utilities.*;
public class BidderDaoImpl implements BidderDao {

	@Override
	public String acceptBid(int tenderId) {
		
		String status = "Bid Assignment Failed";

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement("select * from tender where tender_id=? AND tender_status='Assigned'");
			ps.setInt(1, tenderId);
			rs = ps.executeQuery();
			if (rs.next()) {
				status = "Tender Already Assigned";
			} else {
				
				Bidder bid = bestBids(tenderId);
				
				if(bid==null) {
					status = "No Bids for the Tendor is Found";
				}else {
					pst = con.prepareStatement("update bidder set status = ? where bidder_id=? and status=?");

					pst.setString(1, "Accepted");
					pst.setInt(2, bid.getBidder_id());
					pst.setString(3, "Pending");

					int x = pst.executeUpdate();
					if (x > 0) {
						status = "Bid Has Been Accepted Successfully!";
						TenderDaoImpl dao = new TenderDaoImpl();
//						status = status + "\n" + dao.assignTender(tenderId);
						status = status + "\n" + dao.assignTender(tenderId);
					}
				}
				
				
			}
		} catch (SQLException e) {

			status = status + "Error: " + e.getMessage();
//			e.printStackTrace();
		} finally {

			DBUtil.closeConnection(con);

			DBUtil.closeConnection(ps);
			
			DBUtil.closeConnection(pst);
		}
		return status;
	}

	@Override
	public String rejectBid(int tenderId) {
		
		String status = "Bid Rejection Failed";

		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("update bidder set status = ? where tender_id=? and status = ?");

			ps.setString(1, "Rejected");
			ps.setInt(2, tenderId);
			ps.setString(3, "Pending");

			int x = ps.executeUpdate();
			if (x > 0)
				status = "Bid Has Been Rejected Successfully!";

		} catch (SQLException e) {

			status = status + "Error: " + e.getMessage();
//			e.printStackTrace();
		} finally {

			DBUtil.closeConnection(con);

			DBUtil.closeConnection(ps);
		}
		return status;

	}

	@Override
	public String bidTender(Bidder bidder) {

		String status = "Tender Bidding Failed!";
		
		Connection con = DBUtil.provideConnection();

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("insert into bidder(vendor_id,tender_id,bid_amount,status,biddate) values(?,?,?,?,sysdate())");

			ps.setString(1, bidder.getVendor_id());

			ps.setInt(2, bidder.getTender_id());

			ps.setInt(3, bidder.getBid_amount());

			ps.setString(4, bidder.getStatus());

			int x = ps.executeUpdate();

			if (x > 0)
				status = "You have successfully Bid for the tender";

		} catch (SQLException e) {
			status = status + " Duplicate Bid Found or Invalid Tender Details Found";
//			e.printStackTrace();
		} finally {

			DBUtil.closeConnection(con);

			DBUtil.closeConnection(ps);
		}

		return status;
	}

	@Override
	public List<Bidder> getAllBidsOfaTender(int tenderId) {

		List<Bidder> bidderList = new ArrayList<Bidder>();

		Connection con = DBUtil.provideConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement("select * from bidder where tender_id=?");

			ps.setInt(1, tenderId);

			rs = ps.executeQuery();

			while (rs.next()) {
				Bidder bidder = new Bidder();

				bidder.setBid_amount(rs.getInt("bid_amount"));
				bidder.setBidder_id(rs.getInt("bidder_id"));
				bidder.setStatus(rs.getString("status"));
				bidder.setTender_id(rs.getInt("tender_id"));
				bidder.setVendor_id(rs.getString("vendor_id"));

				bidderList.add(bidder);
			}

		} catch (SQLException e) {
//			System.out.println("No Bids Found.........");
//			e.printStackTrace();

			System.out.println("Exception occurred....");
		} finally {

			DBUtil.closeConnection(con);

			DBUtil.closeConnection(ps);

			DBUtil.closeConnection(rs);
		}

		return bidderList;
	}

	@Override
	public List<Bidder> getAllBidsOfaVendor(String vendorId) {
		List<Bidder> bidderList = new ArrayList<Bidder>();

		Connection con = DBUtil.provideConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement("select * from bidder where vendor_id=?");

			ps.setString(1, vendorId);

			rs = ps.executeQuery();

			while (rs.next()) {
				Bidder bidder = new Bidder();
				
				bidder.setBidder_id(rs.getInt("bidder_id"));
				bidder.setVendor_id(rs.getString("vendor_id"));
				bidder.setTender_id(rs.getInt("tender_id"));
				bidder.setBid_amount(rs.getInt("bid_amount"));
				bidder.setStatus(rs.getString("status"));				

				bidderList.add(bidder);
			}

		} catch (SQLException e) {

			System.out.println("Exception occurred....");
//			e.printStackTrace();
		} finally {

			DBUtil.closeConnection(con);

			DBUtil.closeConnection(ps);

			DBUtil.closeConnection(rs);
		}

		return bidderList;
	}

	@Override
	public String getStatusOfABid(int tid,String vendorId) {

		String status = "Bid Not Found";

		Connection con = DBUtil.provideConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement("select * from bidder where tender_id=? AND vendorid=?");

			ps.setInt(1, tid);
			ps.setString(2, vendorId);

			rs = ps.executeQuery();

			if (rs.next()) {
				status = rs.getString("status");
			}

		} catch (SQLException e) {
			status = status + " Exception Occured";
//			e.printStackTrace();
		} finally {

			DBUtil.closeConnection(con);

			DBUtil.closeConnection(ps);

			DBUtil.closeConnection(rs);
		}

		return status;

	}

	@Override
	public Bidder bestBids(int tendorId) {
		
		Bidder bidder = null;
		
		Connection con = DBUtil.provideConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement("select * from bidder where tender_id=? AND bid_amount = "
					+ "(select min(bid_amount) from bidder where tender_id=?) AND biddate = "
					+ "(select min(biddate) from bidder where tender_id=?)");

			ps.setInt(1, tendorId);
			ps.setInt(2, tendorId);
			ps.setInt(3, tendorId);

			rs = ps.executeQuery();

			if (rs.next()) {
				bidder = new Bidder();
				
				bidder.setBidder_id(rs.getInt("bidder_id"));
				bidder.setVendor_id(rs.getString("vendor_id"));
				bidder.setTender_id(rs.getInt("temder_id"));
				bidder.setBid_amount(rs.getInt("bid_amount"));
				bidder.setStatus(rs.getString("status"));
			}

		} catch (SQLException e) {

			System.out.println("Exception occurred....");
//			e.printStackTrace();
		} finally {

			DBUtil.closeConnection(ps);

			DBUtil.closeConnection(rs);
		}

		
		return bidder;
	}

}