package com.example.zj.androidstudy.material

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zj.androidstudy.R
import com.example.zj.androidstudy.adapter.RecyclerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 * BottomSheet效果，即从屏幕底部向上滑的效果
 */
class BottomSheetFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return initView(inflater.inflate(R.layout.fragment_bottom_sheet, container, false))
    }

    private fun initView(view: View): View {
        val mNsvBottomSheet = view.findViewById<NestedScrollView>(R.id.nsv_bottom_sheet)
        val mRvBottomSheet = view.findViewById<RecyclerView>(R.id.rv_bottom_sheet)
        mRvBottomSheet.layoutManager = LinearLayoutManager(requireActivity())
        mRvBottomSheet.adapter = RecyclerViewAdapter(requireActivity())

        val behavior = BottomSheetBehavior.from(mNsvBottomSheet)
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // 这里是bottomSheet 状态的改变
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // 这里是拖拽中的回调，根据slideOffset可以做一些动画
            }
        })
        return view
    }
}