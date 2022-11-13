package TMS_main_folder.Dao_files;
import java.util.List;
import TMS_main_folder.Bean_classes.Bidder;

public interface BidderDao {

	public String acceptBid(int tenderId);

	public String rejectBid(int tendorId);

	public String bidTender(Bidder bidder);

	public List<Bidder> getAllBidsOfaTender(int tenderId);

	public List<Bidder> getAllBidsOfaVendor(String vendorId);

//	public String getStatusOfABid(int tid,String vendorId);

	public Bidder bestBids(int tenderId);

	public String getStatusOfABid(int tid, String username);
	
}