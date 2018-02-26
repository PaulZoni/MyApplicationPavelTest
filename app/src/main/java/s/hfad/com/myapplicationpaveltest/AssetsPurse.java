package s.hfad.com.myapplicationpaveltest;


import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class AssetsPurse implements ModelPurse{
    @Override
    public ArrayList input(Context context,ArrayList<Transaction> list) {


        try {
            FileInputStream fileInputStream = context.openFileInput(MainPresenterPurse.fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            list = (ArrayList<Transaction>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public ArrayList output(Context context,ArrayList<Transaction> list) {


        try {
            FileOutputStream fileOutputStream = context.openFileOutput(MainPresenterPurse.fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }







        return null;
    }
}
