package com.example.lk_epk.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.lk_epk.MyApplication
import com.example.lk_epk.R
import com.example.lk_epk.fragment.*
import com.example.lk_epk.util.BaseActivity
import com.example.lk_epk.util.BaseFragment
import com.example.lk_epk.util.OkSocketManager
import com.example.lk_epk.util.showToast
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {
    private lateinit var selectFragment: BaseFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bv_battery.BatteryView()
        bv_battery.setProgress(50)
        replaceFragment(ScanAFragment())
        linConnect.setOnClickListener(this)

        tbLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tabChange(tab)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun tabChange(tab: TabLayout.Tab) {
        val position = tab.position
        if (position == 0) {
            replaceFragment(ScanAFragment())
        } else if (position == 1) {
            replaceFragment(ScanBFragment())
        } else if (position == 2) {
            replaceFragment(ScanBackAFragment())
        } else if (position == 3) {
            replaceFragment(ScanBackBFragment())
        } else if (position == 4) {
            replaceFragment(AlignFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        selectFragment = fragment as BaseFragment
        selectFragment.getActivityContext(this)
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.commit()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.linConnect ->{
                if (OkSocketManager.manager.isConnect){
                    resources.getString(R.string.connect_succeeded).showToast(MyApplication.context)
                }else{
                    selectFragment.connectSocket()
                }
            }
        }
    }
}
