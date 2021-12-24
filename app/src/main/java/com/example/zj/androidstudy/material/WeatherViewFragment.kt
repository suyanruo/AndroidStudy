package com.example.zj.androidstudy.material

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.zj.androidstudy.R
import com.example.zj.androidstudy.view.weather.WeatherView

/**
 * 天气动画.
 */
class WeatherViewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return initView(inflater.inflate(R.layout.fragment_weather_view, container, false))
    }

    private fun initView(view: View): View {
        val weatherView = view.findViewById<WeatherView>(R.id.weather_view)
        val tvClickMe = view.findViewById<TextView>(R.id.tv_click_me)

        weatherView.setOnClickListener {
            tvClickMe.visibility = View.GONE
            weatherView.startAnim()
        }
        return view
    }
}