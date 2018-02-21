package s.hfad.com.myapplicationpaveltest;


import java.util.HashMap;

public class MainPresenter {

    double SUM;
    private IView vive;
    private ModelInterface model;
    private  HashMap<String,Double> value=new HashMap<>();

    MainPresenter(IView vive,HashMap<String,Double> value){
        this.vive=vive;
        model=new ModelSUM();
        this.value=value;
    }

    public void onGetButtonClicked(){

        String sumText=vive.getTextEdit().getText().toString();
        String SPLeft=String.valueOf(vive.getLeftSpiner().getSelectedItem());
        String SPRight=String.valueOf(vive.getRightSpiner().getSelectedItem());

        if (value.isEmpty()){
            vive.getTextView().setText("Нет соединения с интернетом");
        }else {
            switch (SPLeft){

                case "USD":
                    if (SPRight.equals("RUB")){
                        SUM=model.count(sumText,value.get("USD"));

                    }else if (SPRight.equals("EUR")){
                        SUM=model.count(sumText,value.get("USD"),value.get("EUR"));
                    }else if (SPLeft.equals("CHF")){
                        SUM=model.count(sumText,value.get("USD"),value.get("CHF"));
                    }

                    break;

                case "EUR":
                    if (SPRight.equals("RUB")){
                        SUM=model.count(sumText,value.get("EUR"));
                    }else if (SPRight.equals("USD")){
                        SUM=model.count(sumText,value.get("EUR"),value.get("USD"));
                    }else if (SPLeft.equals("CHF")){
                        SUM=model.count(sumText,value.get("EUR"),value.get("CHF"));
                    }
                    break;

                case "RUB":

                    if (SPRight.equals("USD")){
                        SUM=model.countRub(sumText,value.get("USD"));
                    }else if (SPRight.equals("EUR")){
                        SUM=model.countRub(sumText,value.get("USD"));
                    }else if (SPLeft.equals("CHF")){
                        SUM=model.countRub(sumText,value.get("CHF"));
                    }
                    break;

                case "CHF":
                    if (SPRight.equals("RUB")){
                        SUM=model.count(sumText,value.get("CHF"));
                    }else if (SPLeft.equals("USD")){
                        SUM=model.count(sumText,value.get("CHF"),value.get("USD"));
                    }else if (SPLeft.equals("EUR")){
                        SUM=model.count(sumText,value.get("CHF"),value.get("EUR"));
                    }
                    break;
            }
            vive.getTextView().setText(String.valueOf(SUM));
        }



    }

}




