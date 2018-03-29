package s.hfad.com.myapplicationpaveltest;


import java.util.HashMap;

class ModelSUM implements ModelInterface{

    @Override
    public double sumTest(String left, String right, HashMap<String,Double> value, String s){
        double result=0;
        double sum=(double)Double.valueOf(s);

        if (right.equals("RUB")){
            double leftSum=value.get(left);

            result=(leftSum*sum);


        }else if (left.equals("RUB")){
            double rightSum= value.get(right);
            result=(sum/rightSum);

        }else {
            double leftSum= value.get(left);
            double rightSum= value.get(right);
            if (leftSum>rightSum){

                result=((leftSum*sum)/rightSum);

            }else if (leftSum<rightSum){
                result=((sum*leftSum)/rightSum);
            }
        }

        return result;
    }

}