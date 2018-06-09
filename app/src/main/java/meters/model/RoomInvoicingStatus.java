package meters.model;

/**
 * Created by Lenovo on 29/01/2017.
 */
public class RoomInvoicingStatus {
    private  String number;
    private String block;
    private  String location;
    private String invoiceDate;

    public RoomInvoicingStatus() {

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getToString()
    {
        StringBuilder builder=new StringBuilder(64);
        builder.append("Apartment:  ");
        builder.append("<b>"+this.number+"<b>"+System.getProperty("line.separator"));
        builder.append("Block:    ");
        builder.append("<b>"+this.block+"<b>"+System.getProperty("line.separator"));
        builder.append("Location:    ");
        builder.append("<b>"+this.location+"<b>"+System.getProperty("line.separator"));
        builder.append("Last Invoice Date    :");
        builder.append("<b>"+this.invoiceDate+"<b>");

        return builder.toString();
    }

}
