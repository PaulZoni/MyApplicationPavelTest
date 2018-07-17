package s.hfad.com.myapplicationpaveltest.Interface;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public interface IView {
    TextView getTextView();
    EditText getTextEdit();
    Spinner getLeftSpiner();
    Spinner getRightSpiner();
}
