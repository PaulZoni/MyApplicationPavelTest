package s.hfad.com.myapplicationpaveltest.my_enum;


public enum languageEnum {

    RUS("ru"),
    USA("en"),
    FRA("fr");
    private String description;

    languageEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}


