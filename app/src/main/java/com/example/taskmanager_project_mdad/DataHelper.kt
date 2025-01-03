package com.example.taskmanager_project_mdad
import android.content.Context
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale

class DataHelper(context: Context) {

    private val sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    private val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())

    private var isTimerRunning = false
    private var remainingTime:Long? = 0L
    private var sessionCount:Int? =0
    private var isBreak:Boolean? = false
    private var selectedStudyTime:Long? = 25L
    private var selectedSmallBreakTime:Long? = 5L
    private var selectedLargeBreakTime:Long? = 15L


    init
    {
        isTimerRunning = sharedPref.getBoolean(TIMER_RUNNING_KEY, false)
        remainingTime = sharedPref.getLong(REMAINING_TIME_KEY,0L)
        sessionCount = sharedPref.getInt(SESSION_COUNT_KEY,0)
        isBreak = sharedPref.getBoolean(IS_BREAK_KEY,false)
        selectedStudyTime = sharedPref.getLong(SELECTED_STUDY_TIME_KEY,25L)
        selectedSmallBreakTime = sharedPref.getLong(SELECTED_SMALL_BREAK_TIME_KEY,5L)
        selectedLargeBreakTime = sharedPref.getLong(SELECTED_LARGE_BREAK_TIME_KEY,15L)

    }


    fun saveRemainingTime(time: Long) {
        with(sharedPref.edit()) {
            putLong(REMAINING_TIME_KEY, time)
            apply()
        }
    }

    fun getRemainingTime(): Long {
        return sharedPref.getLong(REMAINING_TIME_KEY, 0L)
    }

    fun saveSessionCount(count: Int) {
        with(sharedPref.edit()) {
            putInt(SESSION_COUNT_KEY, count)
            apply()
        }
    }

    fun getSessionCount(): Int {
        return sharedPref.getInt(SESSION_COUNT_KEY, 0)
    }

    fun saveIsBreak(isBreak: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(IS_BREAK_KEY, isBreak)
            apply()
        }
    }

    fun getIsBreak(): Boolean {
        return sharedPref.getBoolean(IS_BREAK_KEY, false)
    }

    fun saveSelectedStudyTime(time: Long) {
        with(sharedPref.edit()) {
            putLong(SELECTED_STUDY_TIME_KEY, time)
            apply()
        }
    }

    fun getSelectedStudyTime(): Long {
        return sharedPref.getLong(SELECTED_STUDY_TIME_KEY, 25L) // Default 25 minutes
    }

    fun saveSelectedSmallBreakTime(time: Long) {
        with(sharedPref.edit()) {
            putLong(SELECTED_SMALL_BREAK_TIME_KEY, time)
            apply()
        }
    }

    fun getSelectedSmallBreakTime(): Long {
        return sharedPref.getLong(SELECTED_SMALL_BREAK_TIME_KEY, 5L) // Default 5 minutes
    }

    fun saveSelectedLargeBreakTime(time: Long) {
        with(sharedPref.edit()) {
            putLong(SELECTED_LARGE_BREAK_TIME_KEY, time)
            apply()
        }
    }

    fun getSelectedLargeBreakTime(): Long {
        return sharedPref.getLong(SELECTED_LARGE_BREAK_TIME_KEY, 15L) // Default 15 minutes
    }

    fun saveTimerRunning(isRunning: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(TIMER_RUNNING_KEY, isRunning)
            apply()
        }
    }

    fun isTimerRunning(): Boolean {
        return sharedPref.getBoolean(TIMER_RUNNING_KEY, false)
    }

    companion object {
        const val PREFERENCES = "pomodoroPreferences"
        const val REMAINING_TIME_KEY = "remainingTime"
        const val SESSION_COUNT_KEY = "sessionCount"
        const val IS_BREAK_KEY = "isBreak"
        const val SELECTED_STUDY_TIME_KEY = "selectedStudyTime"
        const val SELECTED_SMALL_BREAK_TIME_KEY = "selectedSmallBreakTime"
        const val SELECTED_LARGE_BREAK_TIME_KEY = "selectedLargeBreakTime"
        const val TIMER_RUNNING_KEY = "timerRunning"
    }
}