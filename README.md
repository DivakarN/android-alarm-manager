# Android Alarm Manager - Kotlin

### Introduction:

This project is created with the intention to understand the working of 
Alarm Manager which can trigger particular event for every x mins.

In this project, You will get to know how you can use broadcast receiver
for Alarm Manager, to start alarm manager and to stop alarm manager given
in a simple format

----------------------------------------------------------------------------------------------------

### Installation:

No specific installation is required for this project.

----------------------------------------------------------------------------------------------------

### Configuration:

The time Interval for invoking alarm should be configured in the activity

```
var timeInterval:Long = 60000
```

----------------------------------------------------------------------------------------------------

### Handler Part:

#### Receiver

The broadcast receiver is used to invoke an event inside the app

```
class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context,"This toast will be shown every X minutes", Toast.LENGTH_SHORT).show()
        \\Include your custom function here
    }
    
}
```

#### Alarm Manager

##### Initialization

```
private var mAlarmManager : AlarmManager? = null
```

##### Starting an Alarm
```
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
```

##### Stopping an Alarm
```
    fun stopAlarmManager() {
        val mIntent = Intent(this, MyReceiver::class.java)
        val mPendingIntent = PendingIntent.getBroadcast(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        mAlarmManager!!.cancel(mPendingIntent)
    }
```

----------------------------------------------------------------------------------------------------

### Usage / Example -

#### As a component

You can take MyReceiver.kt file and include in your project

You can use include below code in your activity and invoke it straight away
```
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
```

```
    fun stopAlarmManager() {
        val mIntent = Intent(this, MyReceiver::class.java)
        val mPendingIntent = PendingIntent.getBroadcast(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        mAlarmManager!!.cancel(mPendingIntent)
    }
```

#### As an Individual Activity

You can include MyReceiver.kt and AlarmManagerActivity.kt in your project
