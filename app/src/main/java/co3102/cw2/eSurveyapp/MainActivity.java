package co3102.cw2.eSurveyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button option1, option2, option3, option4, adminDash;
    private ArrayList<Question> questionArrayList;
    Random random;
    int questionsAttempted = 1, currentPos;
    String userID;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    Button mLogoutBtn, submitAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adminDash = findViewById(R.id.adminDash);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        questionArrayList = new ArrayList<>();
        random = new Random();

        getQuestion(questionArrayList);
        currentPos = random.nextInt(questionArrayList.size());

        userID = firebaseAuth.getCurrentUser().getUid();

        if(userID.equals("yi10WW5yHgWIPYAKvP56JU82pLZ2")) {
            adminDash.setVisibility(View.VISIBLE);
            adminDash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, admin.class));
                }
            });
        }

        option1.setText(questionArrayList.get(0).getQuestion());
        option2.setText(questionArrayList.get(1).getQuestion());
        option3.setText(questionArrayList.get(2).getQuestion());
        option4.setText(questionArrayList.get(3).getQuestion());

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, questionActivity.class));
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, questionActivity.class));
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, questionActivity.class));
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, questionActivity.class));
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

    private void getQuestion(ArrayList<Question> questionArrayList) {
        questionArrayList.add(new Question("question", "answer1", "answer2", "answer3", "answer4"));
        questionArrayList.add(new Question("question 2", "answer1", "answer2", "answer3", "answer4"));
        questionArrayList.add(new Question("question 3", "answer1", "answer2", "answer3", "answer4"));
        questionArrayList.add(new Question("question 4", "answer1", "answer2", "answer3", "answer4"));
    }
}