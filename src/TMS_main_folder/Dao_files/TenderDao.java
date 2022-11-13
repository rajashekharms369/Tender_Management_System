package TMS_main_folder.Dao_files;
import java.util.List;
import TMS_main_folder.Bean_classes.*;
public interface TenderDao {
	public List<Tender> getAllTenders();
	
	public String createTenders(Tender tender);
	
	public boolean removeTender(int tenderid);
	
	public String updateTender(Tender tender);
	
	public Tender getTenderDataById(int tenderId);
	
	public String getTenderStatus(int tenderId);
	
	public String assignTender(int tenderId);

	public List<Tender> getAllAssignedTenders();
	
	public int getTenderId(Tender tender);

	public String createTender(Tender tender);
}
