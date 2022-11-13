package TMS_main_folder.Bean_classes;

public class Vendor {
	private String vendor_id;
	private String vendor_password;
	private String vendor_name;
	private String vendor_email;
	private String vendor_mobno;
	private String company;
	private String address;
	
	public Vendor() {
		super();
	}
	
	public Vendor(String username, String vendor_password) {
		super();
		this.vendor_id = username;
		this.vendor_password = vendor_password;
	}
	
	public Vendor(String vendor_id, String vendor_password, String vendor_name, String vendor_email, String vendor_mobno, String company, String address) {
		super();
		this.vendor_id = vendor_id;
		this.vendor_password = vendor_password;
		this.vendor_name = vendor_name;
		this.vendor_email = vendor_email;
		this.vendor_mobno = vendor_mobno;
		this.company = company;
		this.address = address;
	}

	public String getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}

	public String getVendor_password() {
		return vendor_password;
	}

	public void setVendor_password(String vendor_password) {
		this.vendor_password = vendor_password;
	}

	public String getVendor_name() {
		return vendor_name;
	}

	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}

	public String getVendor_email() {
		return vendor_email;
	}

	public void setVendor_email(String vendor_email) {
		this.vendor_email = vendor_email;
	}

	public String getVendor_mobno() {
		return vendor_mobno;
	}

	public void setVendor_mobno(String vendor_mobno) {
		this.vendor_mobno = vendor_mobno;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Vendor [vid="+vendor_id+", vendor_password= "+vendor_password+", vendor name= "+vendor_name+", vendor_email="+vendor_email+", vendor mobile no.="+vendor_mobno+", company="+company+", address="+address+"]";
	}
}
