package com.geeui.demoforrobotsdk
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.geeui.demoforrobotsdk.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.leitianpai.robotsdk.RobotService
import com.leitianpai.robotsdk.callback.SensorCallback
import com.leitianpai.robotsdk.commandlib.Light
import com.leitianpai.robotsdk.commandlib.RobotRemoteConsts
import com.leitianpai.robotsdk.message.ActionMessage
import com.leitianpai.robotsdk.message.AntennaLightMessage
import com.leitianpai.robotsdk.message.AntennaMessage

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mRobotService: RobotService? = null

    private val mMessage = ActionMessage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        mRobotService = RobotService.getInstance(this)

        val callback: SensorCallback = object : SensorCallback {
            override fun onTapResponse() {
                Log.d(TAG, "onTapResponse: ++++++++++++++++=")
            }

            override fun onDoubleTapResponse() {
                Log.d(TAG, "onDoubleTapResponse: ")
            }

            override fun onLongPressResponse() {
                Log.d(TAG, "onLongPressResponse: ")
            }

            override fun onFallBackend() {
                Log.d(TAG, "onFallBackend: ")
            }

            override fun onFallForward() {
                Log.d(TAG, "onFallForward: ")
            }

            override fun onFallRight() {
                Log.d(TAG, "onFallRight: ")
            }

            override fun onFallLeft() {
                Log.d(TAG, "onFallLeft: ")
            }

            override fun onTof() {
                Log.d(TAG, "onTof: ")
            }
        }
        binding.fab.setOnClickListener(View.OnClickListener { view ->
            Log.d(TAG, "onClick: ")
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab)
                .setAction("Action", null).show()
        })
        // 打开舵机
        binding.fab.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onClick: button1")
            //                mRobotService?.robotControlCommand(RobotRemoteConsts.POWER_CONTROL);
                            mRobotService?.robotOpenMotor();
//            mRobotService?.sendLongCommand("speechDance", "from_third")
        })
        //关闭舵机
        binding.fab1.setOnClickListener(View.OnClickListener { //                mRobotService?.robotControlCommand(RobotRemoteConsts.POWER_DOWN);
            mRobotService?.robotCloseMotor()
            Log.d(TAG, "onClick: button2")
        })
        // 向前走
        binding.fab2.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onClick: button3")
            mMessage[63, 2] = 3
            mRobotService?.robotActionCommand(mMessage)
        })
        // 向后走
        binding.fab3.setOnClickListener(View.OnClickListener {
            val antennaMessage = AntennaMessage()
            antennaMessage[3, 2, 300] = 60
            Log.d(TAG, "onClick: button4")
            //                mRobotService?.robotControlCommand(RobotRemoteConsts.WALK_BACKWARD);
            mRobotService?.robotAntennaMotion(antennaMessage)
        })

        // 向左走
        binding.fab4.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onClick: button5")
            val alMessage = AntennaLightMessage()
            alMessage.set(Light.RED)
            mRobotService?.robotAntennaLight(alMessage)
        })

        // 注册sensor
        binding.fab5.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onClick: button6")
            //                mRobotService?.robotControlCommand(RobotRemoteConsts.GO_RIGHT);
            //                mRobotService?.robotCloseAntennaLight();
            //                mRobotService?.robotControlSound();
            //                mRobotService?.robotControlStatusBar();
            try {
                mRobotService?.robotRegisterSensorCallback(callback)
            } catch (e: RemoteException) {
                throw RuntimeException(e)
            }
        })
        binding.fab6.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onClick: button7")
            //                mRobotService?.robotControlCommand(RobotRemoteConsts.TURN_TO_THE_LEFT);
            try {
                mRobotService?.robotUnregisterSensor()
            } catch (e: RemoteException) {
                throw RuntimeException(e)
            }
            //                mRobotService?.robotControlStatusBar(StatusBarCmd.COMMAND_SHOW_CHARGING);
        })
        binding.fab7.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onClick: button8")
            //                mRobotService?.robotControlCommand(RobotRemoteConsts.TURN_TO_THE_RIGHT);
            //                mRobotService?.robotControlStatusBar(StatusBarCmd.COMMAND_HIDE_CHARGING);
            //                mRobotService?.robotControlSound("a0003");
            mRobotService?.robotPlayTTs("你好你好")
        })

        //打开sensor
        binding.fab8.setOnClickListener(View.OnClickListener { mRobotService?.robotOpenSensor() })

        //关闭sensor
        binding.fab9.setOnClickListener(View.OnClickListener { mRobotService?.robotCloseSensor() })

        // 打开face模式
        binding.fab10.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onClick: robotStartExpression222")
            //                mRobotService?.robotStartExpression("h0280");
            mRobotService?.sendLongCommand("speechDance", "from_third")
//            mRobotService?.unbindService()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        RobotService.getInstance(applicationContext).unbindService()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}