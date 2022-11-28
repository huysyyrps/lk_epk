package com.example.lk_epk.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lk_epk.R
import com.example.lk_epk.util.BaseActivity
import com.example.lk_epk.fragment.ScanAFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bv_battery.BatteryView()
        bv_battery.setProgress(50)
        replaceFragment(ScanAFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val scanAFragment = fragment as ScanAFragment
        scanAFragment.getActivityContext(this)
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.commit()
    }
}
