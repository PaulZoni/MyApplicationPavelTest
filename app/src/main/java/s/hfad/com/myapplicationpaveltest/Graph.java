package s.hfad.com.myapplicationpaveltest;


import android.content.Context;



import java.util.ArrayList;

import io.reactivex.Observable;

public class Graph {

    Context context;
    Graph(Context context){
        this.context=context;
    }

    public ArrayList<Integer> graf(){

        ArrayList<Transaction>transactions=new ArrayList<>();
        ArrayList<Integer>listX=new ArrayList<>();


        ModelPurse purse=new AssetsPurse();
        transactions= purse.input(context,transactions);


        for (int i = 0; i < transactions.size() ; i++) {
            listX.add(transactions.get(i).getSum());
        }




        return listX;
    }




    public Observable<ArrayList<Integer>> getGraph(){
        return Observable.create(observableEmitter->{


            observableEmitter.onNext(graf());
        });
    }

}
