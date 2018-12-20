package sanaebadi.info.teacherhandler.handler

import android.app.Application
import sanaebadi.info.teacherhandler.R
import uk.co.chrisjenx.calligraphy.CalligraphyConfig



class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/droidsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
        //....

    }
}