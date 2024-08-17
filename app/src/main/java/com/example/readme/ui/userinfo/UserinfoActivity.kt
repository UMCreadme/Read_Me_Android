package com.example.readme.ui.userinfo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.readme.R
import com.example.readme.data.entities.UserData
import com.example.readme.ui.MainActivity

class UserinfoActivity : AppCompatActivity() {

    private val userinfoViewModel: UserinfoViewModel by viewModels()
    private lateinit var nicknameEditText: EditText
    private lateinit var idEditText: EditText
    private lateinit var nicknameErrorTextView: TextView
    private lateinit var idErrorTextView: TextView
    private lateinit var submitButton: Button
    private lateinit var idDuplicateTextView: TextView
    private var uniqueId: String? = null
    private var email: String? = null
    private var categoryIdList: List<Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userinfo)

        uniqueId = intent.getStringExtra("uniqueId")
        email = intent.getStringExtra("email")
        categoryIdList = intent.getIntegerArrayListExtra("categoryIdList")

        nicknameEditText = findViewById(R.id.nickname_et)
        idEditText = findViewById(R.id.id_et)
        nicknameErrorTextView = findViewById(R.id.nicknameError_tv)
        idErrorTextView = findViewById(R.id.idnameError_tv)
        idDuplicateTextView = findViewById(R.id.idnameduplicateError_tv)
        submitButton = findViewById(R.id.userinfo_next_btn)

        nicknameEditText.addTextChangedListener(textWatcher)
        idEditText.addTextChangedListener(textWatcher)

        submitButton.setOnClickListener {
            submitUserInfo()
        }


        userinfoViewModel.member4005Error.observe(this) { isMember4005 ->
            if (isMember4005) {
                idDuplicateTextView.visibility = View.VISIBLE
                idDuplicateTextView.text = "*아이디 중복"
                idDuplicateTextView.setTextColor(Color.RED)

                idDuplicateTextView.setTypeface(null, android.graphics.Typeface.BOLD)
                Toast.makeText(this, "다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                idDuplicateTextView.visibility = View.GONE
                idDuplicateTextView.text = ""
                goToMainActivity()
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            validateInputs()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun validateInputs() {
        val nickname = nicknameEditText.text.toString()
        val id = idEditText.text.toString()

        val isNicknameValid = nickname.length <= 12
        val isIdValid = id.length <= 30

        nicknameErrorTextView.visibility = if (isNicknameValid) View.GONE else View.VISIBLE
        nicknameErrorTextView.text = if (!isNicknameValid) "*12자 이상입니다." else ""

        idErrorTextView.visibility = if (isIdValid) View.GONE else View.VISIBLE
        idErrorTextView.text = if (!isIdValid) "*30자 이상입니다." else ""

        submitButton.isEnabled = isNicknameValid && isIdValid
    }

    private fun submitUserInfo() {
        val nickname = nicknameEditText.text.toString()
        val account = idEditText.text.toString()

        if (nickname.isNotEmpty() && account.isNotEmpty()) {
            val userInfo = UserData(
                uniqueId = uniqueId ?: "",
                email = email ?: "",
                nickname = nickname,
                account = account,
                categoryIdList = categoryIdList ?: listOf()
            )

            userinfoViewModel.sendSignUpInfo(userInfo)
        }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
