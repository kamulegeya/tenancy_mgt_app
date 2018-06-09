package meters.model;

@SuppressWarnings("serial")
public class AnnualTotals extends Apartments {
private double invoices;
private double receipts;
	public AnnualTotals() {
		// TODO Auto-generated constructor stub
	}
	public AnnualTotals(int _id, int block_id, String number, String b,
			String l, int locationid,double i,double r, String u) {
		super(_id, block_id, number, b, l, locationid, u);
		this.invoices=i;
		this.receipts=r;
		// TODO Auto-generated constructor stub
	}
	public double getInvoices() {
		return invoices;
	}
	public void setInvoices(double invoices) {
		this.invoices = invoices;
	}
	public double getReceipts() {
		return receipts;
	}
	public void setReceipts(double receipts) {
		this.receipts = receipts;
	}
	
	
}
