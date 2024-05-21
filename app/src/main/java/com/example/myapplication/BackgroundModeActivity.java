package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

public class BackgroundModeActivity extends AppCompatActivity {
    private int count01; // 放在类级别的计数器变量
    private int count02;
    private TextView counter01;
    private TextView counter02;
    private TextView[] group1;
    private TextView[] group2;
    private Button swapButton;
    private Button resetButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backgroundmode);

        // 初始化SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // 从SharedPreferences中读取topScore和bottomScore的值
        int topScore = sharedPreferences.getInt("topScore", 0);
        int bottomScore = sharedPreferences.getInt("bottomScore", 0);
        // 将topScore和bottomScore的值分别赋给count01和count02
        count01 = topScore;
        count02 = bottomScore;

        // 初始化counter01和counter02
        counter01 = findViewById(R.id.counter01);
        counter01.setText("左方: " + count01);
        counter02 = findViewById(R.id.counter02);
        counter02.setText("右方: " + count02);

        // 找到按钮并设置点击监听器
        Button button01 = findViewById(R.id.button01);
        Button button02 = findViewById(R.id.button02);
        Button button03 = findViewById(R.id.button03);
        Button button04 = findViewById(R.id.button04);
        Button button05 = findViewById(R.id.button05);
        Button button06 = findViewById(R.id.button06);
        swapButton = findViewById(R.id.swapButton);
        resetButton = findViewById(R.id.resetButton); // 新增重置按钮

        button01.setOnClickListener(clickListener);
        button02.setOnClickListener(clickListener);
        button03.setOnClickListener(clickListener);
        button04.setOnClickListener(clickListener);
        button05.setOnClickListener(clickListener);
        button06.setOnClickListener(clickListener);

        // 找到返回主页按钮并设置点击监听器
        Button backToMainButton = findViewById(R.id.backToMainButton);
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个跳转到MainActivity的Intent
                Intent intent = new Intent(BackgroundModeActivity.this, MainActivity.class);
                // 启动MainActivity
                startActivity(intent);
                // 结束当前活动
                finish();
            }
        });

        swapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 交换分数逻辑
                int temp = count01;
                count01 = count02;
                count02 = temp;

                // 更新分数显示
                counter01.setText("左方: " + count01);
                counter02.setText("右方: " + count02);

                // 保存新的分数到SharedPreferences
                saveScores();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() { // 设置重置按钮的点击监听器
            @Override
            public void onClick(View v) {
                // 重置count01和count02的值
                count01 = 0;
                count02 = 0;

                // 更新显示
                counter01.setText("左方: " + count01);
                counter02.setText("右方: " + count02);

                // 更新SharedPreferences中的值
                saveScores();
            }
        });

        // 初始化group1和group2
        group1 = new TextView[]{
                findViewById(R.id.t1),
                findViewById(R.id.t2),
                findViewById(R.id.t3),
                findViewById(R.id.t4),
                findViewById(R.id.t5),
                findViewById(R.id.t6)
        };

        group2 = new TextView[]{
                findViewById(R.id.t7),
                findViewById(R.id.t8),
                findViewById(R.id.t9),
                findViewById(R.id.t10),
                findViewById(R.id.t11),
                findViewById(R.id.t12)
        };
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("得分方");
        builder.setMessage("請問哪邊得分？");
        builder.setNegativeButton("左方", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 用户点击了确定按钮
                count01++;
                counter01.setText("左方: " + count01);

                // 保存新的分数到SharedPreferences
                saveScores();
            }
        });
        builder.setPositiveButton("右方", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 用户点击了确定按钮
                count02++;
                counter02.setText("右方: " + count02);

                // 保存新的分数到SharedPreferences
                saveScores();
            }
        });

        builder.show();
    }

    private void saveScores() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("topScore", count01);
        editor.putInt("bottomScore", count02);
        editor.apply();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 弹出确认对话框
            showConfirmationDialog();
        }
    };
}
