package s.hfad.com.myapplicationpaveltest.model_assets;


public class Menu {
    private String text;
    private String photoId;
    private String url;

    public String getPhotoId() {
        return photoId;
    }

    public String getText() {
        return text;
    }

    public String getUrl(){
        return url;
    }

    public Menu(String text, String photoId, String url) {
        this.text = text;
        this.photoId=photoId;
        this.url=url;
    }
}

