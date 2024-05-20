package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ChangeTeamNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_team_name)

        // 设置工具栏
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            // 点击返回按钮时，结束当前活动并返回到上一活动（即主页面）
            finish()
        }
    }

    // 确认更改按钮的点击事件处理方法
    fun onConfirmClicked(view: View) {
        // 获取输入的新名称
        val newNameTeamA = findViewById<EditText>(R.id.edit_text_team_a).text.toString()
        val newNameTeamB = findViewById<EditText>(R.id.edit_text_team_b).text.toString()

        // 更新主界面中 Team A 和 Team B 的名称
        val intent = Intent()
        intent.putExtra("newNameTeamA", newNameTeamA)
        intent.putExtra("newNameTeamB", newNameTeamB)
        setResult(RESULT_OK, intent)

        // 结束当前活动并返回到上一个活动（即主页面）
        finish()
    }
}
