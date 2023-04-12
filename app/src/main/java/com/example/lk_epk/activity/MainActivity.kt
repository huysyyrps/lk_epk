package com.example.lk_epk.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.lk_epk.MyApplication
import com.example.lk_epk.R
import com.example.lk_epk.fragment.*
import com.example.lk_epk.util.BaseActivity
import com.example.lk_epk.util.BaseFragment
import com.example.lk_epk.util.showToast
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {
    private lateinit var selectFragment: BaseFragment
    private lateinit var fragmentManager: FragmentManager
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTagList = arrayOf("ScanAFragment", "ScanBFragment", "ScanBackAFragment", "AlignFragment", "AlignFragment")
    private lateinit var currentFragmen: Fragment

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()
        bv_battery.BatteryView()
        bv_battery.setProgress(50)
        linConnect.setOnClickListener(this)

        tbLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tabChange(tab)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        mFragmentList.add(0, ScanAFragment())
        mFragmentList.add(1, ScanBFragment())
        mFragmentList.add(2, ScanBackAFragment())
        mFragmentList.add(3, ScanBackBFragment())
        mFragmentList.add(4, AlignFragment())
        currentFragmen = mFragmentList[0];
        // 初始化首次进入时的Fragment
        fragmentManager = supportFragmentManager;
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.add(R.id.flFragment, currentFragmen, mFragmentTagList[0])
        transaction.commitAllowingStateLoss()

        selectFragment = currentFragmen as BaseFragment
        selectFragment.getActivityContext(this)
    }
    /*
    权限申请
     */
    private fun requestPermission() {
        val requestList = ArrayList<String>()
        requestList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        requestList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (requestList.isNotEmpty()) {
            PermissionX.init(this)
                .permissions(requestList)
                .onExplainRequestReason { scope, deniedList ->
                    val message = "需要您同意以下权限才能正常使用"
                    scope.showRequestReasonDialog(deniedList, message, "同意", "取消")
                }
                .request { allGranted, _, deniedList ->
                    if (allGranted) {
                        Log.e("TAG","所有申请的权限都已通过")
                    } else {
                        Log.e("TAG","您拒绝了如下权限：$deniedList")
                        finish()
                    }
                }
        }

    }

    private fun tabChange(tab: TabLayout.Tab) {
        val position = tab.position
        if (position == 0) {
            switchFragment(mFragmentList[0], mFragmentTagList[0]);
        } else if (position == 1) {
            switchFragment(mFragmentList[1], mFragmentTagList[1]);
        } else if (position == 2) {
            switchFragment(mFragmentList[2], mFragmentTagList[2]);
        } else if (position == 3) {
            switchFragment(mFragmentList[3], mFragmentTagList[3]);
        } else if (position == 4) {
            switchFragment(mFragmentList[4], mFragmentTagList[4]);
        }
    }

    // 转换Fragment
    fun switchFragment(fragment: Fragment, tag: String?) {
        selectFragment = fragment as BaseFragment
        selectFragment.getActivityContext(this)

        if (currentFragmen !== fragment) {
            val transaction = fragmentManager.beginTransaction()
            if (!fragment.isAdded) {
                // 没有添加过:
                // 隐藏当前的，添加新的，显示新的
                transaction.hide(currentFragmen).add(R.id.flFragment, fragment, tag).show(fragment)
            } else {
                // 隐藏当前的，显示新的
                transaction.hide(currentFragmen).show(fragment)
            }
            currentFragmen = fragment
            transaction.commitAllowingStateLoss()
        }
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.linConnect ->{
                if (selectFragment.connectStatue){
                    resources.getString(R.string.connect_succeeded).showToast(MyApplication.context)
                }else{
                    selectFragment.connectSocket()
                }
            }
        }
    }
}
