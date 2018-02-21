package s.hfad.com.myapplicationpaveltest;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by God on 13.02.2018.
 */

public interface IView {
    public TextView getTextView();
    public EditText getTextEdit();
    public Spinner getLeftSpiner();
    public Spinner getRightSpiner();

}
