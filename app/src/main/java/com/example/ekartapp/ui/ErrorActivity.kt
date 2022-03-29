package com.example.ekartapp.ui

import android.os.Bundle
import com.example.ekartapp.R
import com.example.ekartapp.ui.base.BaseActivity

class ErrorActivity : BaseActivity() {
    override fun provideLayoutId(): Int =R.layout.activity_error
    override fun setupView(savedInstanceState: Bundle?) {}
}