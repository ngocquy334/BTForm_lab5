package com.example.form_lab5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.form_lab5.databinding.ActivityHomeBinding
import com.example.form_lab5.model.User

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getSerializableExtra("EXTRA_USER") as? User

        if (user != null) {
            binding.tvUserInfo.text = """
                Họ và tên: ${user.fullName}
                Email: ${user.email}
                Mật khẩu: ${"*".repeat(user.password.length)}
                Số điện thoại: ${user.phone}
            """.trimIndent()
        }
    }
}