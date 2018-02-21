package s.hfad.com.myapplicationpaveltest;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class AssetsPurse implements ModelPurse{
    @Override
    public ArrayList input(ArrayList list) {

        try {
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream("Assets.ser"));
            //Object ob=ois.ois.readObject();
            Object o= (Object) ois.readObject();
            list= (ArrayList) o;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public ArrayList output(ArrayList list) {


        try {
            FileOutputStream fs = new FileOutputStream("Assets.ser");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(list);
            os.close();
        } catch (java.io.IOException e1) {
            e1.printStackTrace();
        }


        /*try {
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("Assets.ser"));
            oos.writeObject(list);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return null;
    }
}
