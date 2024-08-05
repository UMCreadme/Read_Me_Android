package com.example.readme

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.readme.R
import com.example.readme.ui.category.CategoryViewModel
import com.example.readme.ui.userinfo.UserinfoActivity

class CategoryActivity : AppCompatActivity() {

    private val viewModel: CategoryViewModel by viewModels()
    private  lateinit var toggleButtons: List<ToggleButton>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)



    toggleButtons = listOf(
        findViewById(R.id.checkBox_1),
        findViewById(R.id.checkBox_2),
        findViewById(R.id.checkBox_3),
        findViewById(R.id.checkBox_4),
        findViewById(R.id.checkBox_5),
        findViewById(R.id.checkBox_6),
        findViewById(R.id.checkBox_7),
        findViewById(R.id.checkBox_8),
        findViewById(R.id.checkBox_9),
        findViewById(R.id.checkBox_10),
        findViewById(R.id.checkBox_11),
        findViewById(R.id.checkBox_12),
        findViewById(R.id.checkBox_13),
        findViewById(R.id.checkBox_14),
        findViewById(R.id.checkBox_15),
        findViewById(R.id.checkBox_16),
        findViewById(R.id.checkBox_17),
        findViewById(R.id.checkBox_18),

    )
     for (toggleButton  in toggleButtons){
         toggleButton.setOnClickListener{onToggleButtonClicked(it)}
     }



        val btnNext: Button = findViewById(R.id.category_next_btn)
        btnNext.setOnClickListener {
            viewModel.selectedToggleIds.observe(this, Observer { selectedIds ->
                // 선택된 토글 버튼 ID 리스트를 사용할 수 있습니다.
                Log.d("CategoryActivity", "Observed Selected Toggle IDs: $selectedIds")
            })
            navigateToUserInfoActivity()
        }

    }

    private fun onToggleButtonClicked(view: View?) {
        val isChecked = (view as ToggleButton).isChecked
        val customId = view.tag.toString().toInt()
        if(!isChecked || viewModel.canSelectMore()){
            viewModel.toggleSelection(isChecked, customId)
        }
        else{
            view.isChecked = false
              }

    }
    private fun navigateToUserInfoActivity() {
        val intent = Intent(this, UserinfoActivity::class.java)
        startActivity(intent)
        finish()
    }
}
