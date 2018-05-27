package s.hfad.com.myapplicationpaveltest;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;



public interface IView {
    public TextView getTextView();
    public EditText getTextEdit();
    public Spinner getLeftSpiner();
    public Spinner getRightSpiner();

}
