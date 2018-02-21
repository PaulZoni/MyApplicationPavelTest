package s.hfad.com.myapplicationpaveltest;


import java.io.Serializable;

public  class Transaction implements Serializable {

    private int sum;
    private String comment;
    private String data;

    public void setSum(int sum){
        this.sum=sum;
    }

    public void setComment(String comment){
        this.comment=comment;
    }

    public void setDat(String data){
        this.data=data;
    }

    public int getSum(){
        return sum;
    }

    public String getComment(){
        return comment;
    }

    public String getDat(){
        return data;
    }
}
