package com.bhavnathacker.jettasks.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.bhavnathacker.jettasks.MainActivity
import com.wcz.fingerprintrecognitionmanager.FingerManager
import com.wcz.fingerprintrecognitionmanager.FingerManager.SupportResult
import com.wcz.fingerprintrecognitionmanager.callback.SimpleFingerCallback
import com.wcz.fingerprintrecognitionmanager.dialog.CommonTipDialog
import com.wcz.fingerprintrecognitionmanager.dialog.CommonTipDialog.OnDialogButtonsClickListener
import com.wcz.fingerprintrecognitionmanager.util.PhoneInfoCheck
import dagger.hilt.android.internal.Contexts.getApplication

public interface FingerPrintCallback{
    fun OnSuccess();
}

class FingerPrint {
    private fun showToast(ctx:Context,content: String) {
        Toast.makeText(ctx, content, Toast.LENGTH_SHORT).show()
    }
    private fun showOpenSettingDialog(ctx:Context,msg: String) {
        val openFingerprintTipDialog = CommonTipDialog(ctx)
        openFingerprintTipDialog.setSingleButton(false)
        openFingerprintTipDialog.setContentText("您还未录入指纹信息，是否现在录入?")
        openFingerprintTipDialog.setOnDialogButtonsClickListener(object :
            OnDialogButtonsClickListener {
            override fun onCancelClick(v: View) {}
            override fun onConfirmClick(v: View) {
                startFingerprint(ctx)
            }
        })
        openFingerprintTipDialog.show()
    }
    fun startFingerprint(ctx:Context) {
        val BRAND = Build.BRAND
        //Log.d(MainActivity.TAG, "BRAND:$BRAND")
        PhoneInfoCheck.getInstance(ctx, BRAND).startFingerprint()
    }

    public fun check(ctx: ComponentActivity, cb:FingerPrintCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when (FingerManager.checkSupport(ctx)) {
                SupportResult.DEVICE_UNSUPPORTED -> showToast(ctx,"您的设备不支持指纹")
                SupportResult.SUPPORT_WITHOUT_KEYGUARD ->                     //设备支持但未处于安全保护中（你的设备必须是使用屏幕锁保护的，这个屏幕锁可以是password，PIN或者图案都行）
                    showOpenSettingDialog(ctx,"您还未录屏幕锁保护，是否现在开启?")

                SupportResult.SUPPORT_WITHOUT_DATA -> showOpenSettingDialog(ctx,"您还未录入指纹信息，是否现在录入?")
                SupportResult.SUPPORT -> FingerManager.build().setApplication(ctx.application)
                    .setTitle("指纹验证")
                    .setDes("请按下指纹")
                    .setNegativeText("取消") //                                    .setFingerDialogApi23(new MyFingerDialog())//如果你需要自定义android P 以下系统弹窗就设置,注意需要继承BaseFingerDialog，不设置会使用默认弹窗
                    .setFingerCallback(object : SimpleFingerCallback() {
                        override fun onSucceed() {
                            showToast(ctx,"验证成功")
                            cb.OnSuccess()
                        }

                        override fun onFailed() {
                            showToast(ctx,"指纹无法识别")
                        }

                        override fun onChange() {
                            FingerManager.updateFingerData(ctx)
                            check(ctx,cb)
                        }
                    })
                    .create()
                    .startListener(ctx)

                else -> {}
            }
        }
    }
}