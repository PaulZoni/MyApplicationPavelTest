package s.hfad.com.myapplicationpaveltest;


import java.util.ArrayList;
import java.util.Date;

public class ModelPurseTopping implements ModelPurseTop{

    @Override
    public void buttonOk(int sum,String comment) {
        ModelPurse modelPurse=new AssetsPurse();
        ArrayList<Transaction> list=new ArrayList();

        list=modelPurse.input(list);


        Date date=new Date();
        Transaction transaction=new Transaction();
        transaction.setSum(sum);
        transaction.setComment(comment);
        transaction.setDat(String.valueOf(date));

        list.add(transaction);

        modelPurse.output(list);

    }

    @Override
    public String buttonAll() {
        ModelPurse modelPurse=new AssetsPurse();
        ArrayList<Transaction> list=new ArrayList<>();

        list=modelPurse.input(list);

        ArrayList<String> listText=new ArrayList<>();
        ArrayList<String> listData=new ArrayList<>();
        ArrayList<Integer> listSum=new ArrayList<>();
        ArrayList<String> strings=new ArrayList<>();

        for (int i=0;i<list.size();i++){
            listText.add(list.get(i).getComment());
            listData.add(list.get(i).getDat());
            listSum.add(list.get(i).getSum());
            strings.add(String.valueOf(listSum.get(i))+" "+listText.get(i)+" "+listData.get(i));
        }

        StringBuilder builder=new StringBuilder();
        for (int i = 0; i <strings.size() ; i++) {
            builder.append(strings.get(i)+"\n");
        }

        String st= String.valueOf(builder);


        return st;
    }
}




class Test{
    public static void main(String[] args) {
        ModelPurse modelPurse=new AssetsPurse();

        Transaction transaction=new Transaction();
        transaction.setDat("dq");
        transaction.setComment("ew");
        transaction.setSum(123);




        ArrayList list=new ArrayList();

        list.add(transaction);


        modelPurse.output(list);

        //ModelPurseTop modl=new ModelPurseTopping();

        //modl.buttonOk(23,"ew");
        //System.out.println(modl.buttonAll());
    }
}












