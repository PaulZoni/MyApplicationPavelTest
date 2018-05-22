package s.hfad.com.myapplicationpaveltest.modelAsets;


public class Menu {
    String text;
    String photoId;
    private String url;

    public String getUrl(){
        return url;
    }

    public Menu(String text, String photoId, String url) {
        this.text = text;
        this.photoId=photoId;
        this.url=url;
    }
}







