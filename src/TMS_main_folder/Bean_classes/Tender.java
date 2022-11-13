package TMS_main_folder.Bean_classes;

public class Tender {
	private int tender_id;
	private String tender_name;
	private String tender_type;
	private int tender_price;
	private String tender_description;
	private String tender_status;
	private String tender_deadline;
	
	public Tender() {
		super();
	}
	
	public Tender(int tender_id, String tender_name, String tender_type, int tender_price, String tender_description, String tender_status) {
		this.tender_id = tender_id;
		this.tender_name = tender_name;
		this.tender_type = tender_type;
		this.tender_price = tender_price;
		this.tender_description = tender_description;
		this.tender_status = tender_status;
	}

	public int getTender_id() {
		return tender_id;
	}

	public void setTender_id(int tender_id) {
		this.tender_id = tender_id;
	}

	public String getTender_name() {
		return tender_name;
	}

	public void setTender_name(String tender_name) {
		this.tender_name = tender_name;
	}

	public String getTender_type() {
		return tender_type;
	}

	public void setTender_type(String tender_type) {
		this.tender_type = tender_type;
	}

	public int getTender_price() {
		return tender_price;
	}

	public void setTender_price(int tender_price) {
		this.tender_price = tender_price;
	}

	public String getTender_description() {
		return tender_description;
	}

	public void setTender_description(String tender_description) {
		this.tender_description = tender_description;
	}

	public String getTender_status() {
		return tender_status;
	}

	public void setTender_status(String tender_status) {
		this.tender_status = tender_status;
	}

	public String getTender_deadline() {
		return tender_deadline;
	}

	public void setTender_deadline(String tender_deadline) {
		this.tender_deadline = tender_deadline;
	}
	
	@Override
	public String toString() {
		return "Tender [tender id="+tender_id+", tender_name="+tender_name+", tender_type="+tender_type+", tender_price="+tender_price+", tender_description="+tender_description+"tender_status="+tender_status+"]";
	}
}
