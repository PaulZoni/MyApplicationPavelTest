package s.hfad.com.myapplicationpaveltest.modelAsets;


import java.util.ArrayList;

public class KeyWord {
    private String[]USDWord=new String[]{"usd","доллор","доллар","долар","длор","сша"};
    private String[]EURWord=new String[]{"eur","евро","евра"};
    private String[]CHFWord=new String[]{"CHF","швецария"};
    private String[]AUDWord=new String[]{"AUD","австралия"};
    private String[]AZNWord=new String[]{"AZN","азербаджан","манат"};
    private String[]GBPWord=new String[]{"GBP","фунт","королевство"};
    private String[]AMDWord=new String[]{"AMD","армении","драм"};
    private String[]BYNWord=new String[]{"BYN","беларусия","евра"};
    private String[]BGNWord=new String[]{"BGN","балгария","болгария"};
    private String[]BRLWord=new String[]{"BRL","бразилия","брозилия","реал"};
    private String[]HUFWord=new String[]{"HUF","венгрия"};
    private String[]HKDWord=new String[]{"HKD","гонконг"};
    private String[]DKKWord=new String[]{"DKK","дания"};
    private String[]INRWord=new String[]{"INR","индия"};
    private String[]KZTWord=new String[]{"KZT","казахи","тенге"};

    private ArrayList<String[]>mList=new ArrayList<>();
    private String[]result={"USD", "EUR", "CHF", "AUD", "AZN",
                            "GBP", "AMD", "BYN", "BGN", "BRL", "HUF", "HKD", "DKK", "INR", "KZT"};

    public String wordAnsver(String word){
        mList.add(USDWord);
        mList.add(EURWord);
        mList.add(CHFWord);
        mList.add(AUDWord);
        mList.add(AZNWord);
        mList.add(GBPWord);
        mList.add(AMDWord);
        mList.add(BYNWord);
        mList.add(BGNWord);
        mList.add(BRLWord);
        mList.add(HUFWord);
        mList.add(HKDWord);
        mList.add(DKKWord);
        mList.add(INRWord);
        mList.add(KZTWord);

        for (int i = 0; i <mList.size() ; i++) {
            for (int j = 0; j <mList.get(i).length ; j++) {
                if (word.equals(mList.get(i)[j])){
                    return result[i];

                }
            }
        }
        return "нет такого результата";
    }
}
