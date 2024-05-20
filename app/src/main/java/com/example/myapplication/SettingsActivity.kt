// SettingsActivity.kt
package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.EditText


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // 设置工具栏
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            // 点击返回按钮时，结束当前活动并返回到上一个活动（即主画面）
            finish()
        }
    }

    // 更改队伍名称的点击事件处理方法
    fun onChangeTeamNameClicked(view: View) {
        val newNameTeamA = findViewById<EditText>(R.id.edit_text_team_a).text.toString()
        val newNameTeamB = findViewById<EditText>(R.id.edit_text_team_b).text.toString()

        // 將新的隊伍名稱返回給 MainActivity
        val intent = Intent()
        intent.putExtra("newNameTeamA", newNameTeamA)
        intent.putExtra("newNameTeamB", newNameTeamB)
        setResult(RESULT_OK, intent)
        finish()
    }

    // 查看历史纪录的点击事件处理方法
    fun onHistoryRecordClicked(view: View) {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

}

