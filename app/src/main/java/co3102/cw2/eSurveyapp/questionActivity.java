package co3102.cw2.eSurveyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Random;

public class questionActivity extends AppCompatActivity {
    private TextView question, questionNumber;
    private Button option1, option2, option3, option4, mLogoutBtn;
    private ArrayList<Question> questionArrayList;
    Random random;
    int questionsAttempted = 1, currentPos;

    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        question = findViewById(R.id.question);
        questionNumber = findViewById(R.id.dashboard);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        questionArrayList = new ArrayList<>();
        random = new Random();

        getQuestion(questionArrayList);
        currentPos = random.nextInt(questionArrayList.size());
        setDataToView(currentPos);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionsAttempted++;
                currentPos = random.nextInt(questionArrayList.size());
                setDataToView(currentPos);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionsAttempted++;
                currentPos = random.nextInt(questionArrayList.size());
                setDataToView(currentPos);
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionsAttempted++;
                currentPos = random.nextInt(questionArrayList.size());
                setDataToView(currentPos);
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionsAttempted++;
                currentPos = random.nextInt(questionArrayList.size());
                setDataToView(currentPos);
            }
        });

        fStore = FirebaseFirestore.getInstance();

        mLogoutBtn = findViewById(R.id.createQuestion);

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    private void setDataToView(int currentPos) {
        questionNumber.setText("Questions Attemped: " + questionsAttempted);
        question.setText(questionArrayList.get(currentPos).getQuestion());
        option1.setText(questionArrayList.get(currentPos).getOption1());
        option2.setText(questionArrayList.get(currentPos).getOption2());
        option3.setText(questionArrayList.get(currentPos).getOption3());
        option4.setText(questionArrayList.get(currentPos).getOption4());
    }

    private void getQuestion(ArrayList<Question> questionArrayList) {
        questionArrayList.add(new Question("This is a question", "test1", "test2", "test3", "test4"));

    }
}
