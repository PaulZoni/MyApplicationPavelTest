package s.hfad.com.myapplicationpaveltest;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import s.hfad.com.myapplicationpaveltest.modelAsets.KeyWord;

public class FireMissilesDialogFragment extends DialogFragment {

    private KeyWord mKeyWord;
    private TextView textResult;
    private EditText faindEditText;

    public FireMissilesDialogFragment() {
        mKeyWord=new KeyWord();
    }


    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);

    }

    NoticeDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoticeDialogListener) activity;


    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView=inflater.inflate(R.layout.dialog_signin, null);

        builder.setView(dialogView)
                .setPositiveButton(R.string.app_name_Ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        textResult=(TextView)dialogView.findViewById(R.id.TextResult);
                        faindEditText=(EditText)dialogView.findViewById(R.id.editText_find);
                        String string=faindEditText.getText().toString();

                        String answer=mKeyWord.wordAnsver(string);

                        if (answer.equals("USD")){

                            mListener.onDialogPositiveClick(FireMissilesDialogFragment.this);
                        }


                    }
                });

        return builder.create();

    }



}


















