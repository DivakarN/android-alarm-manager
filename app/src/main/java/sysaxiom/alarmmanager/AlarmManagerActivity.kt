package sysaxiom.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.joancolmenero.broadcastreceiverandalarmmanagerkotlin.R

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.File

class AlarmManagerActivity : AppCompatActivity() {

    //region Variable Initialization
    private var mAlarmManager : AlarmManager? = null
    var timeInterval:Long = 60000
    //endregion

    //region Activity Life Cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        buttonActionSetup()
    }
    //endregion

    //region Alarm Manager
    fun startAlarmManager() {
        val mIntent = Intent(this, MyReceiver::class.java)

        val mPendingIntent = PendingIntent.getBroadcast(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        mAlarmManager = this
            .getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mAlarmManager!!.setRepeating(
            AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
            timeInterval, mPendingIntent
        )
    }
    fun stopAlarmManager() {
        val mIntent = Intent(this, MyReceiver::class.java)
        val mPendingIntent = PendingIntent.getBroadcast(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        mAlarmManager!!.cancel(mPendingIntent)
    }
    //endregion

    //region UI Setup
    fun buttonActionSetup() {
        btn_start_alarm.setOnClickListener { view ->
            startAlarmManager()
        }

        btn_cancel_alarm.setOnClickListener {
            stopAlarmManager()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    //endregion

    //region Share Back up Data
    private fun shareBackupData(file: File) {

        val jsonUri = FileProvider.getUriForFile(
            this,
            this.applicationContext.packageName + ".com.joancolmenero.sharejsonfileviaintent.provider",
            file
        )

        Toast.makeText(this,"this is to check if exists ${file.exists()}",Toast.LENGTH_LONG).show()
        if (file.exists()) {
            val intentShareFile = Intent(Intent.ACTION_SEND)
            intentShareFile.putExtra(Intent.EXTRA_STREAM, jsonUri)

            intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivity(Intent.createChooser(intentShareFile, "Share File"))
        }


    }
    //endregion
}
