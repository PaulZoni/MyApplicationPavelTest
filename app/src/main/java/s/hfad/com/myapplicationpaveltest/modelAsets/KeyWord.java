package s.hfad.com.myapplicationpaveltest.modelAsets;



public class KeyWord {
    private String[]USDWord=new String[]{"usd","доллор","доллар","долар","длор","сша"};
    private String[]EURWord=new String[]{"eur","евро","евра"};


    public String wordAnsver(String word){


        for (int i = 0; i <USDWord.length ; i++) {
            if (word.equals(USDWord[i])){
                return "USD";
            }
        }


        for (int i = 0; i <EURWord.length ; i++) {
            if (word.equals(EURWord[i])){
                return "EUR";
            }
        }

        return "нет такого результата";
    }
}
