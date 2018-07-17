package s.hfad.com.myapplicationpaveltest.presenters;


import android.content.Context;
import s.hfad.com.myapplicationpaveltest.Interface.MVPViewFragmentInformation;
import s.hfad.com.myapplicationpaveltest.Interface.PresenterFragmentInformationInterface;



public class PresenterFragmentInformation implements PresenterFragmentInformationInterface<MVPViewFragmentInformation> {

    private MVPViewFragmentInformation view;
    private Context mContext;

    public PresenterFragmentInformation( Context context) {
        mContext = context;
    }

    @Override
    public void attachView(MVPViewFragmentInformation mvpView) {
        view = mvpView;
    }


}
