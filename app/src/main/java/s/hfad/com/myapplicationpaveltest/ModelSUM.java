package s.hfad.com.myapplicationpaveltest;




class ModelSUM implements ModelInterface{

    @Override
    public  double count(String sum,double multiplier) {
        double y= Double.parseDouble(sum);
        double x=y*multiplier;

        return x;
    }

    @Override
    public double count(String sum, double d1, double d2) {
        double y= Double.parseDouble(sum);
        double z=d1*y;
        double x=z/d2;


        return x;
    }


    @Override
    public double countRub(String sum, double d1) {
        double y= Double.parseDouble(sum);
        double x=y/d1;
        return x;
    }


}