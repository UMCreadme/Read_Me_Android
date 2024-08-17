// CategoryActivity.kt
package com.example.readme.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ToggleButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.readme.R
import com.example.readme.ui.userinfo.UserinfoActivity

class CategoryActivity : AppCompatActivity() {

    private val categoryViewModel: CategoryViewModel by viewModels()
    private lateinit var toggleButtons: List<ToggleButton>
    private var uniqueId: String? = null
    private var email: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        uniqueId = intent.getStringExtra("uniqueId")
        email = intent.getStringExtra("email")


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
            findViewById(R.id.checkBox_19),
            findViewById(R.id.checkBox_20),
            findViewById(R.id.checkBox_21)
        )

        for (toggleButton in toggleButtons) {
            toggleButton.setOnClickListener { onToggleButtonClicked(it) }
        }

        val btnNext: Button = findViewById(R.id.category_next_btn)
        btnNext.setOnClickListener {
            goToUserinfoActivity()
        }
    }

    private fun onToggleButtonClicked(view: View?) {
        val isChecked = (view as ToggleButton).isChecked
        val customId = view.tag.toString().toInt()
        if (!isChecked || categoryViewModel.canSelectMore()) {
            categoryViewModel.toggleSelection(isChecked, customId)
        } else {
            view.isChecked = false
        }
    }

    private fun goToUserinfoActivity() {
        val selectedIds = categoryViewModel.selectedToggleIds.value ?: listOf()
        val intent = Intent(this, UserinfoActivity::class.java)
        intent.putIntegerArrayListExtra("categoryIdList", ArrayList(selectedIds))
        intent.putExtra("uniqueId", uniqueId)
        intent.putExtra("email", email)
        startActivity(intent)
        finish()
    }
}