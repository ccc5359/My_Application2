package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.res.Configuration

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var topScoreTextView: TextView
    private lateinit var bottomScoreTextView: TextView
    private lateinit var resetScoreButton: Button
    private lateinit var settingButton: Button
    private lateinit var teamNameTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    private var topScore = 0
    private var bottomScore = 0

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

        // 获取显示隊伍名稱的 TextView
        teamNameTextView = findViewById(R.id.teamNameTextView)

        // 從SharedPreferences中讀取隊伍名稱並設置給TextView
        val teamName = sharedPreferences.getString("teamName", "")
        teamNameTextView.text = teamName
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.topScoreTextView -> {
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    topScore++
                    updateTopScoreTextView()
                } else {
                    bottomScore++
                    updateBottomScoreTextView()
                }
            }
            R.id.bottomScoreTextView -> {
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    bottomScore++
                    updateBottomScoreTextView()
                } else {
                    topScore++
                    updateTopScoreTextView()
                }
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
        updateTopScoreTextView()
        updateBottomScoreTextView()
    }

    companion object {
        const val REQUEST_CODE_CHANGE_TEAM_NAME = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHANGE_TEAM_NAME && resultCode == RESULT_OK) {
            val newNameTeamA = data?.getStringExtra("newNameTeamA")
            val newNameTeamB = data?.getStringExtra("newNameTeamB")
            val teamName = getString(R.string.team_name_format, newNameTeamA, newNameTeamB)
            teamNameTextView.text = teamName
        }
    }
}
