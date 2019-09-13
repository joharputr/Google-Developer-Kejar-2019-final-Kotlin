package com.example.submission2.Reminder

import android.os.Build
import android.os.Bundle
import android.preference.Preference
import android.preference.SwitchPreference
import android.util.Log
import android.widget.Toast
import com.example.submission2.API.NetworkConfig
import com.example.submission2.R
import com.example.submission2.movie.ResultsItemMovie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SettingFragment : android.preference.PreferenceFragment(),
    Preference.OnPreferenceChangeListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)


        val switchDailyReminder = findPreference("key_daily_reminder") as SwitchPreference
        switchDailyReminder.onPreferenceChangeListener = this
        val switchUpcomingReminder = findPreference("key_release_reminder") as SwitchPreference
        switchUpcomingReminder.onPreferenceChangeListener = this
    }

    private var nowPlayMovies = ArrayList<ResultsItemMovie>()

    private val dailyAlarmReceiver = DailyAlarm()
    private val releaseTodayReminder = ReleaseTodayReminder()


    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        val key = preference.key
        val isSet = newValue as Boolean

        if (key == "key_daily_reminder") {
            if (isSet) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dailyAlarmReceiver.setRepeatingAlarm(context)
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dailyAlarmReceiver.cancelAlarm(context)
                }
            }
        } else {
            if (isSet) {
                getTodayRelease()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    releaseTodayReminder.cancelAlarm(context)
                }
            }
        }

        return true
    }


    private fun getTodayRelease() {
        val c = Calendar.getInstance().time
        println("Current time => $c")

        val df = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = df.format(c)

        Log.d("", "getMatchToday: $formattedDate")
        val apiService = NetworkConfig.getApiService()
        val call = apiService.getmovieToday(
            "9b3efc88fed3cb0e25a0849788f05166",
            LANGUAGE,
            formattedDate,
            formattedDate
        )

        call.enqueue(object : Callback<com.example.submission2.movie.Response> {
            override fun onResponse(
                call: Call<com.example.submission2.movie.Response>,
                response: Response<com.example.submission2.movie.Response>
            ) {
                if (response.isSuccessful()) {
                    val todayMovie = ArrayList<ResultsItemMovie>()
                    nowPlayMovies = response.body()?.results!!
                    for (i in nowPlayMovies.indices) {

                        val movie = nowPlayMovies[i]
                        Log.d("", "onResponseFIlem: $todayMovie")
                        Log.d("", "onResponse: " + todayMovie.size)
                    }

                    for (movieResult in nowPlayMovies) {
                        todayMovie.addAll(listOf(movieResult))
                        Log.v("adakah", "" + todayMovie.size)
                    }


                    Log.d("", "onResponseFIlem: $todayMovie")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        releaseTodayReminder.setRepeatingAlarm(context, todayMovie)
                    }
                } else {
                    Toast.makeText(activity, R.string.toast_failed, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<com.example.submission2.movie.Response>,
                t: Throwable
            ) {
                Toast.makeText(activity, R.string.toast_failed, Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        fun newInstance() = SettingFragment()
        private val LANGUAGE = "en-us"
    }
}
