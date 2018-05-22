package s.hfad.com.myapplicationpaveltest.MyEnum;


public enum languageEnum {

    RUS("RUS"),
    USA("USA"),
    FRA("FRA");
    private String description;

    languageEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}


