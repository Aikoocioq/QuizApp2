package pl.edu.pb.wi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private Button trueButton;
private Button falseButton;
private Button nextButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);

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
                    currentIndex = (currentIndex + 1);
                }
                setNextQuestion();
            }
        });
    }
    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if (userAnswer == correctAnswer) {
            resultMessageId = R.string.correct_answer;
            points = points + 1;
        } else {
            resultMessageId = R.string.incorrect_answer;
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