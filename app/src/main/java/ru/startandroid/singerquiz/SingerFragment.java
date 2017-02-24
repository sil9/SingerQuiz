package ru.startandroid.singerquiz;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class SingerFragment extends Fragment implements View.OnClickListener {


    public final static int RESULT_REQUEST_CODE = 0;
    public final static int SETTINGS_REQUEST_CODE = 1;
    String[] names;
    public static final String ARG_NUMBER_QUESTIONS = "number_questions";
    private static final String ARG_NUMBER_VARIANTS = "number_variants";
    private static final String BTN_TEXT_KEY = "btn_text";
    private static final String BTN_STATE_KEY = "btn_state";
    public static final String CORRECT_KEY = "correct_answers";
    public HashMap<String, String> hashNames;
    private int prepodsKol = 0;
    private String answerJPG;
    private int numberQuestions = 0;
    private int currentQuestion = 0;
    private int correctAnswers = 0;
    private int lastNumberVariants = 0;
    private int currentNumberVariants = 0;
    private TextView textViewQuestionNumber;
    TextView AnswerTextView;
    private ImageView imageView;
    private ConstraintLayout linearLayout;
    private List<String> prepodsList = new ArrayList<>();
    private List<String> nameForButtonList = new ArrayList<>();
    int numberVariants;


    private int[] buttonsThreeID = {R.id.btn1,
            R.id.btn2,
            R.id.btn3

    };

    private int[] buttonsTwoID = {R.id.btn1,
            R.id.btn2};

    private int[] buttonsFourID = {R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4};

    private List<Button> buttons = new ArrayList<>();
    private List<Button> allButtons = new ArrayList<>();

    private String[] prepodsFileNames;
    private boolean preferenceChanged = false;

    private AssetManager assets;
    private SecureRandom random;
    private Handler handler;
    private SharedPreferences sharedPreferences;
    View view;


    OnResetQuizCallback callback;

    public interface OnResetQuizCallback {
        void onResetQuiz();
    }


    public static SingerFragment newInstance(String numberQuestions, String numberVariants) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_NUMBER_QUESTIONS, numberQuestions);
        arguments.putString(ARG_NUMBER_VARIANTS, numberVariants);
        SingerFragment singerFragment = new SingerFragment();
        singerFragment.setArguments(arguments);
        return singerFragment;
    }


    private void showButtons() {
        for (Button button : allButtons) {
            button.setVisibility(View.INVISIBLE);
        }
        for (Button button : buttons) {
            button.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (OnResetQuizCallback) context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);

        numberQuestions = Integer.parseInt(getArguments().getString(ARG_NUMBER_QUESTIONS, null));
        numberVariants = Integer.parseInt(getArguments().getString(ARG_NUMBER_VARIANTS, null));


        currentNumberVariants = lastNumberVariants = numberVariants;


        assets = getActivity().getAssets();
        random = new SecureRandom();
        handler = new Handler();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        hashNames = new HashMap();
        hashNames.put("Alicia Keys.jpg", "Alicia Keys");
        hashNames.put("Avril Lavigne.jpg", "Avril Lavigne");
        hashNames.put("Beyonce Knowles.jpg", "Beyonce Knowles");
        hashNames.put("Britney Spears.jpg", "Britney Spears");
        hashNames.put("Christina Milian.jpg", "Christina Milian");
        hashNames.put("Ciara.jpg", "Ciara");
        hashNames.put("Gwen Stefani.jpg", "Gwen Stefani");
        hashNames.put("Iggy Azalea.jpg", "Iggy Azalea");
        hashNames.put("Jennifer Lopez.jpg", "Jennifer Lopez");
        hashNames.put("Jessica Simpson.jpg", "Jessica Simpson");
        hashNames.put("Katharine McPhee.jpg", "Katharine McPhee");
        hashNames.put("Katy Perry.jpg", "Katy Perry");
        hashNames.put("Lady Gaga.jpg", "Lady Gaga");
        hashNames.put("Madonna.jpg", "Madonna");
        hashNames.put("Miley Cyrus.jpg", "Miley Cyrus");
        hashNames.put("Nicole Scherzinger.jpg", "Nicole Scherzinger");
        hashNames.put("Rihanna.jpg", "Rihanna");
        hashNames.put("Selena Gomez.jpg", "Selena Gomez");
        hashNames.put("Shakira.jpg", "Shakira");
        hashNames.put("B'yanka.jpg", "B'yanka");
        hashNames.put("Inna Hot.jpg", "Inna Hot");
        hashNames.put("Nicki Minaj.jpg", "Nicki Minaj");
        hashNames.put("Olga Seryabkina.jpg", "Olga Seryabkina");
        hashNames.put("Tara Reid.jpg", "Tara Reid");
        hashNames.put("Nyusha.jpg", "Nyusha");


        names = getResources().getStringArray(R.array.names);

        try {
            prepodsFileNames = assets.list("prepods");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.prepod_layout_fragment_six, container, false);

        allButtons.clear();
        for (int i : buttonsFourID) {
            Button button = (Button) view.findViewById(i);
            allButtons.add(button);
        }
        Log.d("12345", "onCreateView ");
        doSettings();

        textViewQuestionNumber = (TextView) view.findViewById(R.id.textViewQestionNumber);
        AnswerTextView = (TextView) view.findViewById(R.id.AnswerTextView);

        imageView = (ImageView) view.findViewById(R.id.imageView);

        linearLayout = (ConstraintLayout) view.findViewById(R.id.linearLayout);

        InputStream inputStream = null;
        if (savedInstanceState == null) {
            loadPrepods();
            loadNextPrepodQuestion();
        } else {
            HashMap<Integer, String> textMap = (HashMap<Integer, String>) savedInstanceState.getSerializable(BTN_TEXT_KEY);
            for (Button button : buttons) {
                button.setText(textMap.get(button.getId()));
            }

            HashMap<Integer, Boolean> stateMap = (HashMap<Integer, Boolean>) savedInstanceState.getSerializable(BTN_STATE_KEY);
            for (Button button : buttons) {
                button.setEnabled(stateMap.get(button.getId()));
            }
            try {
                inputStream = assets.open("prepods/" + answerJPG);
                textViewQuestionNumber.setText("Question " + (prepodsKol - prepodsList.size()) + "/" + numberQuestions);
                Drawable drawable = Drawable.createFromStream(inputStream, "prepod");
                imageView.setImageDrawable(drawable);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    private void doSettings() {
        switch (numberVariants) {
            case 2:
                putButtonsInCol(buttonsTwoID);
                break;
            case 3:
                putButtonsInCol(buttonsThreeID);
                break;
            case 4:
                putButtonsInCol(buttonsFourID);
                break;

        }
        showButtons();
    }

    @Override
    public void onStart() {
        super.onStart();

        currentNumberVariants = Integer.parseInt(sharedPreferences.getString(MainActivity.KEY_VARIANTS_PREFERENCES, null));

        if (lastNumberVariants != currentNumberVariants) {
            numberVariants = Integer.parseInt(sharedPreferences.getString(MainActivity.KEY_VARIANTS_PREFERENCES, null));
            lastNumberVariants = currentNumberVariants;
            doSettings();
            loadNames();
            enableButtons();
            int buttonPosition = random.nextInt(buttons.size());
            buttons.get(buttonPosition).setText(hashNames.get(answerJPG));

            for (int btnPosition = 0; btnPosition < buttons.size(); btnPosition++) {
                if (btnPosition != buttonPosition) {
                    Collections.shuffle(nameForButtonList);
                    buttons.get(btnPosition).setText(nameForButtonList.remove(0));

                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(BTN_TEXT_KEY, saveTextButtons());
        outState.putSerializable(BTN_STATE_KEY, saveStateButtons());
    }

    private HashMap<Integer, String> saveTextButtons() {
        HashMap<Integer, String> map = new HashMap<>();
        for (Button button : buttons) {
            map.put(button.getId(), button.getText().toString());
        }
        return map;
    }

    private HashMap<Integer, Boolean> saveStateButtons() {
        HashMap<Integer, Boolean> map = new HashMap<>();
        for (Button button : buttons) {
            map.put(button.getId(), button.isEnabled());
        }
        return map;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Settings:
                startActivityForResult(new Intent(getActivity(), SettingsActivity.class), SETTINGS_REQUEST_CODE);
                break;
            case R.id.Restart:


                RestartDialog dialogFragment = new RestartDialog();
                dialogFragment.setCancelable(false);
                dialogFragment.setTargetFragment(SingerFragment.this, 0);
                dialogFragment.show(getFragmentManager(), "dialog");
                numberQuestions = Integer.parseInt(sharedPreferences.getString(MainActivity.KEY_QUESTIONS_PREFERENCES, null));
                break;
            case R.id.Close:
                getActivity().finish();
                break;

        }
        return true;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public void loadPrepods() {
        prepodsList.clear();
        currentQuestion = 0;
        //  incorrectAnswers = 0;
        correctAnswers = 0;
        for (String item : prepodsFileNames) {
            prepodsList.add(item);
        }
        prepodsKol = prepodsList.size();
    }

    private void loadNames() {
        nameForButtonList.clear();
        for (String item : names) {
            nameForButtonList.add(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case RESULT_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    loadPrepods();
                    numberQuestions = Integer.parseInt(sharedPreferences.getString(MainActivity.KEY_QUESTIONS_PREFERENCES, null));
                    loadNextPrepodQuestion();
                } else {
                    getActivity().finish();
                }
                break;
            case SETTINGS_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Log.d("PIAT", "onActivityResult()");
                    callback.onResetQuiz();
                }
                break;
        }

    }

    public void loadNextPrepodQuestion() {
        if (currentQuestion == numberQuestions) {
            startActivityForResult(new Intent(getActivity(), ResultActivity.class)
                    .putExtra(CORRECT_KEY, correctAnswers)
                    .putExtra(ARG_NUMBER_QUESTIONS, numberQuestions), RESULT_REQUEST_CODE);


            return;
        }
        loadNames();
        enableButtons();
        int position = random.nextInt(prepodsList.size());
        InputStream inputStream;
        try {
            answerJPG = prepodsList.remove(position);
            inputStream = assets.open("prepods/" + answerJPG);
            textViewQuestionNumber.setText("Question " + (prepodsKol - prepodsList.size()) + "/" + numberQuestions);
            Drawable drawable = Drawable.createFromStream(inputStream, "prepod");
            imageView.setImageDrawable(drawable);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int buttonPosition = random.nextInt(buttons.size());

        buttons.get(buttonPosition).setText(hashNames.get(answerJPG));

        for (int btnPosition = 0; btnPosition < buttons.size(); btnPosition++) {
            if (btnPosition != buttonPosition) {
                Collections.shuffle(nameForButtonList);

                buttons.get(btnPosition).setText(nameForButtonList.remove(0));
            }
        }
        ++currentQuestion;
        if (currentQuestion == 1) {
            return;
        }

    }

    private void enableButtons() {
        for (Button button : buttons) {
            button.setEnabled(true);
        }
    }

    private void disableButtons() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }

    private void putButtonsInCol(int[] ids) {
        buttons.clear();
        for (int i = 0; i < ids.length; i++) {
            Button button = (Button) view.findViewById(ids[i]);
            button.setOnClickListener(this);
            buttons.add(button);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        if (((Button) v).getText().toString().equals(hashNames.get(answerJPG))) {


            AnswerTextView.setVisibility(View.VISIBLE);
            AnswerTextView.setText(R.string.CorrectAnswer);

            AnswerTextView.setTextColor(Color.parseColor("#6fc105"));
            disableButtons();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (isAdded() && getActivity() != null && !getActivity().isFinishing()) {
                        Log.d("ZHEKA", "run");
                        correctAnswers++;

                        loadNextPrepodQuestion();
                        AnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }
            }, 1000);
        } else {
            v.setEnabled(false);

            AnswerTextView.setVisibility(View.VISIBLE);
            AnswerTextView.setText(R.string.IncorrectAnswer);


            AnswerTextView.setTextColor(Color.parseColor("#e20412"));
            disableButtons();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    loadNextPrepodQuestion();
                    AnswerTextView.setVisibility(View.INVISIBLE);

                }
            }, 1000);
        }
    }
}