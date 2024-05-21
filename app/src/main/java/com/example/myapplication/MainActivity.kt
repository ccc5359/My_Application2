package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var topScoreTextView: TextView
    private lateinit var bottomScoreTextView: TextView
    private lateinit var resetScoreButton: Button
    private lateinit var settingButton: Button
    private lateinit var teamATextView: TextView
    private lateinit var teamBTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    private var topScore = 0
    private var bottomScore = 0
    private var count01 = 0 // 新增 count01 属性
    private var count02 = 0 // 新增 count02 属性

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // 获取上方分数 TextView 和设置点击监听器
        topScoreTextView = findViewById(R.id.topScoreTextView)
        topScoreTextView.setOnClickListener(this)

        // 获取下方分数 TextView 和设置点击监听器
        bottomScoreTextView = findViewById(R.id.bottomScoreTextView)
        bottomScoreTextView.setOnClickListener(this)

        // 获取重新计算分数的 Button 和设置点击监听器
        resetScoreButton = findViewById(R.id.resetScoreButton)
        resetScoreButton.setOnClickListener {
            resetScores()
        }

        // 获取设置的 Button 和设置点击监听器
        settingButton = findViewById(R.id.settingButton)
        settingButton.setOnClickListener {
            // 跳转到设置界面
            val intent = Intent(this, SettingsActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_CHANGE_TEAM_NAME)
        }
        // 从SharedPreferences中读取分数并更新显示
                topScore = sharedPreferences.getInt("topScore", 0)
                bottomScore = sharedPreferences.getInt("bottomScore", 0)
                updateTopScoreTextView()
                updateBottomScoreTextView()
    }

    override fun onResume() {
        super.onResume()
        // 从SharedPreferences中读取分数并更新显示
        topScore = sharedPreferences.getInt("topScore", 0)
        bottomScore = sharedPreferences.getInt("bottomScore", 0)
        updateTopScoreTextView()
        updateBottomScoreTextView()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.topScoreTextView -> {
                topScore++
                count01++ // 更新 count01 的值
                updateTopScoreTextView()
                saveScores()
            }
            R.id.bottomScoreTextView -> {
                bottomScore++
                count02++ // 更新 count02 的值
                updateBottomScoreTextView()
                saveScores()
            }
        }
    }

    // 更新上方分数显示
    private fun updateTopScoreTextView() {
        topScoreTextView.text = topScore.toString()
    }

    // 更新下方分数显示
    private fun updateBottomScoreTextView() {
        bottomScoreTextView.text = bottomScore.toString()
    }

    // 重置分数
    private fun resetScores() {
        topScore = 0
        bottomScore = 0
        count01 = 0 // 重置 count01 的值
        count02 = 0 // 重置 count02 的值
        updateTopScoreTextView()
        updateBottomScoreTextView()
        saveScores()
    }

    private fun saveScores() {
        val editor = sharedPreferences.edit()
        editor.putInt("topScore", topScore)
        editor.putInt("bottomScore", bottomScore)
        editor.apply()
    }

    companion object {
        const val REQUEST_CODE_CHANGE_TEAM_NAME = 1
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHANGE_TEAM_NAME && resultCode == android.app.Activity.RESULT_OK) {
            val teamAName = data?.getStringExtra("teamAName")
            val teamBName = data?.getStringExtra("teamBName")
            // 应用文本到 Team A 和 Team B 的 TextView 上
            teamATextView.text = teamAName
            teamBTextView.text = teamBName
        }
    }

}
