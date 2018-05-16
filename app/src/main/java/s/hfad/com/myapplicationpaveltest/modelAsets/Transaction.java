package s.hfad.com.myapplicationpaveltest.modelAsets;



public class Transaction {

    private String sum;
    private String data;
    private String comment;
    private String location;

    public Transaction(String sum, String data, String comment, String location) {
        this.sum = sum;
        this.data = data;
        this.comment = comment;
        this.location = location;
    }

    public String getSum() {
        return sum;
    }

    public String getData() {
        return data;
    }

    public String getComment() {
        return comment;
    }

    public String getLocation() {
        return location;
    }
}
