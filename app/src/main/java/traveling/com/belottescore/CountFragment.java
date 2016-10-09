package traveling.com.belottescore;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountFragment extends DialogFragment {
    private static final String TAG = "tag";
    int hanac = 0;
    Model model;
    int merCombo;
    int dzerCombo;
    int score;
    Realm realm;
    int sumOur = 0;
    int sumYour = 0;
    CheckBox boyCheckbox;
    int asac;
    int team = 0;
    int yourScore;
    RadioButton quanshRadioButton;
    RadioButton surRadioButton;

    public CountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        asac = Integer.parseInt(getArguments().getString("told"));
        team = getArguments().getInt("team");

        final EditText hanacEditText;
        final EditText drsicMenq, drsicDuq;
        Button takButton;

        realm = Realm.getDefaultInstance();
        Button countScoreButton;
        final View view = inflater.inflate(R.layout.fragment_count, container, false);
        hanacEditText = (EditText) view.findViewById(R.id.editText_hanac);
        takButton = (Button) view.findViewById(R.id.button_tak);
        boyCheckbox = (CheckBox) view.findViewById(R.id.checkBox_boy);
        drsicMenq = (EditText) view.findViewById(R.id.editText_drsicMenq);
        drsicDuq = (EditText) view.findViewById(R.id.editText_drsicDuq);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        TextView toldText = (TextView) view.findViewById(R.id.textView_fragment_toldText);
        countScoreButton = (Button) view.findViewById(R.id.button_count);
        quanshRadioButton = (RadioButton) view.findViewById(R.id.radioButton_quansh);
        surRadioButton = (RadioButton)view.findViewById(R.id.radioButton_sur);
        toldText.setText(String.valueOf(asac));



        view.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                getDialog().dismiss();
            }
        });

        takButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                if (drsicMenq.getText().length() == 0) {
                    drsicMenq.setText(String.valueOf(0));
                }
                merCombo = Integer.parseInt(drsicMenq.getText().toString());


                if (drsicDuq.getText().length() == 0) {
                    drsicDuq.setText(String.valueOf(0));
                }
                dzerCombo = Integer.parseInt(drsicDuq.getText().toString());


                sumOur = realm.where(Model.class).findAllSorted("ourScore").last().ourScore;
                sumYour = realm.where(Model.class).findAllSorted("yourScore").last().yourScore;

                score = asac + 16 + merCombo + dzerCombo;
                if (team % 2 != 0) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            model = realm.createObject(Model.class);
                            model.setOurScore(sumOur);
                            model.setYourScore(score + sumYour);
                            model.setToldScore(asac);
                        }
                    });
                } else {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            model = realm.createObject(Model.class);
                            model.setOurScore(sumOur + score);
                            model.setYourScore(sumYour);
                            model.setToldScore(asac);
                        }
                    });
                }
                getDialog().dismiss();
            }
        });


        countScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                if (drsicMenq.getText().length() == 0) {
                    drsicMenq.setText(String.valueOf(0));
                }
                merCombo = Integer.parseInt(drsicMenq.getText().toString());


                if (drsicDuq.getText().length() == 0) {
                    drsicDuq.setText(String.valueOf(0));
                }

                sumOur = realm.where(Model.class).findAllSorted("ourScore").last().ourScore;
                sumYour = realm.where(Model.class).findAllSorted("yourScore").last().yourScore;
                if(Integer.parseInt(hanacEditText.getText().toString())!=0) {
                    hanac = Integer.parseInt(hanacEditText.getText().toString());
                }
                else
                {
                    hanac = 0;

                }
                score = asac + hanac + merCombo;
                dzerCombo = Integer.parseInt(drsicDuq.getText().toString());

                final int ourScore = sumOur + score;
                if (hanac < 16) {
                    yourScore = 16 - hanac + dzerCombo + sumYour;
                } else {
                    yourScore =  dzerCombo + sumYour;
                }

                if (team % 2 != 0) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            model = realm.createObject(Model.class);
                            model.setOurScore(ourScore);
                            model.setYourScore(yourScore);
                            model.setToldScore(asac);


                        }
                    });
                } else {

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            model = realm.createObject(Model.class);
                            if (hanac < 16) {
                                model.setOurScore(sumOur + 16 - hanac + dzerCombo);
                            } else {
                                model.setOurScore(sumOur  + dzerCombo);
                            }
                            model.setYourScore(sumYour + asac + hanac);
                            model.setToldScore(asac);

                        }
                    });
                }


                quanshRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton pCompoundButton, boolean pB) {
                        if (pB) {
                            asac = asac * 2 + dzerCombo + merCombo;

                        }

                    }
                });
                surRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton pCompoundButton, boolean pB) {
                            asac = asac*4+dzerCombo+merCombo;
                    }
                });
                getDialog().dismiss();
            }

        });

        boyCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton pCompoundButton, boolean pB) {
                if (pB) {

                    asac = asac * 2;

                } else {
                    asac = Integer.parseInt(getArguments().getString("told"));

                }

            }
        });


        return view;
    }


}
