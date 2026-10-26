package com.example.form_lab5

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.form_lab5.databinding.ActivityFormLab5Binding
import com.example.form_lab5.model.User
import com.example.form_lab5.validator.FormValidator
import com.google.android.material.snackbar.Snackbar

class FormLab5Activity : AppCompatActivity() {

    private lateinit var binding: ActivityFormLab5Binding

    private var lastClickTime: Long = 0L
    private val CLICK_DEBOUNCE_DELAY = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLab5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRealtimeValidation()
        setupButtons()
    }

    private fun setupRealtimeValidation() {
        binding.etFullName.doAfterTextChanged {
            if (binding.tilFullName.error != null) {
                binding.tilFullName.error = null
                binding.tilFullName.isErrorEnabled = false
            }
        }
        binding.etEmail.doAfterTextChanged {
            if (binding.tilEmail.error != null) {
                binding.tilEmail.error = null
                binding.tilEmail.isErrorEnabled = false
            }
        }
        binding.etPassword.doAfterTextChanged {
            if (binding.tilPassword.error != null) {
                binding.tilPassword.error = null
                binding.tilPassword.isErrorEnabled = false
            }
        }
        binding.etPhone.doAfterTextChanged {
            if (binding.tilPhone.error != null) {
                binding.tilPhone.error = null
                binding.tilPhone.isErrorEnabled = false
            }
        }
    }

    private fun setupButtons() {
        binding.btnRegister.setOnClickListener {
            val currentTime = SystemClock.elapsedRealtime()
            if (currentTime - lastClickTime < CLICK_DEBOUNCE_DELAY) return@setOnClickListener
            lastClickTime = currentTime

            if (validateForm()) {
                val user = User(
                    fullName = binding.etFullName.text.toString().trim(),
                    email = binding.etEmail.text.toString().trim(),
                    password = binding.etPassword.text.toString(),
                    phone = binding.etPhone.text.toString().trim()
                )

                val intent = Intent(this, HomeActivity::class.java).apply {
                    putExtra("EXTRA_USER", user)
                }
                startActivity(intent)
            } else {
                Snackbar.make(
                    binding.root,
                    "Vui lòng kiểm tra lại thông tin đăng ký!",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        binding.btnCancel.setOnClickListener {
            resetForm()
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        var firstErrorView: View? = null

        val name = binding.etFullName.text.toString().trim()
        if (!FormValidator.isValidName(name)) {
            binding.tilFullName.error = "Họ tên phải có ít nhất 3 ký tự"
            if (firstErrorView == null) firstErrorView = binding.etFullName
            isValid = false
        }

        val email = binding.etEmail.text.toString().trim()
        if (!FormValidator.isValidEmail(email)) {
            binding.tilEmail.error = "Email không đúng định dạng"
            if (firstErrorView == null) firstErrorView = binding.etEmail
            isValid = false
        }

        val pass = binding.etPassword.text.toString()
        if (!FormValidator.isValidPassword(pass)) {
            binding.tilPassword.error = "Mật khẩu phải từ 6 ký tự trở lên"
            if (firstErrorView == null) firstErrorView = binding.etPassword
            isValid = false
        }

        val phone = binding.etPhone.text.toString().trim()
        if (!FormValidator.isValidPhone(phone)) {
            binding.tilPhone.error = "Số điện thoại không hợp lệ (gồm 10 số)"
            if (firstErrorView == null) firstErrorView = binding.etPhone
            isValid = false
        }

        firstErrorView?.requestFocus()
        return isValid
    }

    private fun resetForm() {
        binding.etFullName.text?.clear()
        binding.etEmail.text?.clear()
        binding.etPassword.text?.clear()
        binding.etPhone.text?.clear()

        binding.tilFullName.error = null
        binding.tilEmail.error = null
        binding.tilPassword.error = null
        binding.tilPhone.error = null

        binding.etFullName.requestFocus()
    }
}