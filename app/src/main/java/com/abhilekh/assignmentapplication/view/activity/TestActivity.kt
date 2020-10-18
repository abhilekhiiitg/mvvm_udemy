package com.abhilekh.assignmentapplication.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        print("")
        test()
    }

    fun test(){
        print("testing pr commit ")
    }
}