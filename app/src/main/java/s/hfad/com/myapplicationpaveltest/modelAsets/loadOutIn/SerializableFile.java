package s.hfad.com.myapplicationpaveltest.modelAsets.loadOutIn;


import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableFile {
    private Context mContext;

    public SerializableFile(Context context) {
        mContext = context;
    }

    public void outPut(String s){
        try {
            FileOutputStream fileOutputStream = mContext.openFileOutput("sms.ser", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(s);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String inPut(){
        String s = null;
        try {
            FileInputStream fileInputStream = mContext.openFileInput("sms.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            s = (String) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return s;
    }
}
