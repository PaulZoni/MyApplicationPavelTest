package s.hfad.com.myapplicationpaveltest.model_assets;

public class ValutaModel {

     private String name;
     private String value;
     private int photoId;

    public ValutaModel(String name, String value, int photoId) {
        this.name = name;
        this.value = value;
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public int getPhotoId() {
        return photoId;
    }
}
