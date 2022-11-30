package com.example.lk_epk.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.lk_epk.MyApplication
import com.example.lk_epk.R
import com.example.lk_epk.util.BaseActivity
import com.example.lk_epk.fragment.ScanAFragment
import com.example.lk_epk.util.BaseFragment
import com.example.lk_epk.util.showToast
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
    }
    private fun replaceFragment(fragment: Fragment) {
        selectFragment = fragment as ScanAFragment
        selectFragment.getActivityContext(this)
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.commit()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.linConnect ->{
                if (selectFragment.manager.isConnect){
                    resources.getString(R.string.connect_succeeded).showToast(MyApplication.context)
                }else{
                    selectFragment.connectSocket()
                }
            }
        }
    }
}
