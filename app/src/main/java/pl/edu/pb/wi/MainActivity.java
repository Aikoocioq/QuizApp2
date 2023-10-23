package pl.edu.pb.wi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private Button trueButton, falseButton, nextButton, promptButton;
private TextView questionTextView;
private Question[] questions = new Question[] {
        new Question(R.string.q_zimorodek, false),
        new Question(R.string.q_bocian, true),
        new Question(R.string.q_oskub, true),
        new Question(R.string.q_sowa, false),
        new Question(R.string.q_zolna, true),
        new Question(R.string.q_piora, false),
};
private int currentIndex = 0;
private int points = 0;
private static final String QUIZ_TAG = "MainActivity";
private static final String KEY_CURRENT_INDEX = "currentIndex";
public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quiz.CorrectAnswer";
private static final int REQUEST_CODE_PROMPT = 0;
private boolean answerWasShown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG, "Wywołana została metoda cyklu życia: onCreate");
        setContentView(R.layout.activity_main);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        promptButton = findViewById(R.id.hint_button);
        questionTextView = findViewById(R.id.question_text_view);
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
        setNextQuestion();

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex == 99) {
                    currentIndex = 0;
                }
                else {
                    checkAnswerCorrectness(true);
                    currentIndex = (currentIndex + 1);
                }
                setNextQuestion();
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex == 99) {
                    currentIndex = 0;
                }
                else {
                    checkAnswerCorrectness(false);
                    currentIndex = (currentIndex + 1);
                }
                setNextQuestion();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex == 99) {
                    currentIndex = 0;
                }
                else {
                    answerWasShown = false;
                    currentIndex = (currentIndex + 1);
                }
                setNextQuestion();
            }
        });
        promptButton.setOnClickListener((v) -> {
            if (currentIndex == 99) {
                currentIndex = 0;
                setNextQuestion();
            }
            else {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) { return; }
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) { return; }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG, "Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

        @Override
    protected void onStart() {
        super.onStart();
        Log.d("Start", "Wywołana została metoda cyklu życia: onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Stop", "Wywołana została metoda cyklu życia: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Destroy", "Wywołana została metoda cyklu życia: onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Pause", "Wywołana została metoda cyklu życia: onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Resume", "Wywołana została metoda cyklu życia: onResume");
    }

    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if (answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
            answerWasShown = false;
        }
        else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
                points = points + 1;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }
    private void setNextQuestion() {
        if (currentIndex == questions.length) {
            showPoints();
        } else if (currentIndex > questions.length) {
        }
        else {
            questionTextView.setText(questions[currentIndex].getQuestionId());
        }
    }
    private void showPoints() {
        int quantity = currentIndex;
        questionTextView.setText(points + "/" + quantity + " poprawnych odpowiedzi. Naciśnij dowolny przycisk, by spróbować ponownie.");
        currentIndex = 99;
        points = 0;
    }
}