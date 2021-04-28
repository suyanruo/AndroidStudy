package com.example.zj.androidstudy.liveData

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.zj.androidstudy.R
import com.example.zj.androidstudy.viewModel.NameViewModel

/**
 * Created on 4/28/21.
 *
 */
class NameActivity : AppCompatActivity() {
    private lateinit var tvName: TextView
    // Use the 'by viewModels()' Kotlin property delegate
    // from the activity-ktx artifact
    private val model: NameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        tvName = findViewById(R.id.tv_name)

        // Create the observer which updates the UI.
        val nameObserver = Observer<String> { name ->
            tvName.text = name
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.currentName.observe(this, nameObserver)

        model.currentName.value = "Mars"
    }
}