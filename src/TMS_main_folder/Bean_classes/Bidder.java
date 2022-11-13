package TMS_main_folder.Bean_classes;

//import java.time.LocalDate;

public class Bidder {
	private int bidder_id;
	private String vendor_id;
	private int tender_id;
	private int bid_amount;
//	private LocalDate bid_date;
	private String status;
	
	public Bidder() {
		super();
	}
	
	public Bidder(int bidder_id, String vendor_id, int tender_id, int bid_amount, String status) {
		super();
		this.bidder_id = bidder_id;
		this.vendor_id = vendor_id;
		this.tender_id = tender_id;
		this.bid_amount = bid_amount;
		this.status = status;
//		this.bid_date = bid_date;
	}

	public int getBid_amount() {
		return bid_amount;
	}

	public void setBid_amount(int bid_amount) {
		this.bid_amount = bid_amount;
	}

	public int getBidder_id() {
		return bidder_id;
	}

	public void setBidder_id(int bidder_id) {
		this.bidder_id = bidder_id;
	}

	public String getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(String string) {
		this.vendor_id = string;
	}

	public int getTender_id() {
		return tender_id;
	}

	public void setTender_id(int tender_id) {
		this.tender_id = tender_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Bidder [bidder_id="+bidder_id+", vendor_id= "+vendor_id+", tendor_id= "+ tender_id + ", bid_amount="+ bid_amount +", status="+status+"]";
	}

	
}
