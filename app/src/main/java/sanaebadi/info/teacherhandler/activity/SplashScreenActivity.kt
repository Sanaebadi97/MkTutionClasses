package sanaebadi.info.teacherhandler.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import sanaebadi.info.teacherhandler.R
import sanaebadi.info.teacherhandler.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var animation: Animation

    private val SPLASH_TIME_OUT: Long = 2000
    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        handler=Handler()
        animation = AnimationUtils.loadAnimation(this, R.anim.transition)
        binding.imgLogo.animation = animation
        binding.txtSplash.animation = animation

        handler!!.postDelayed({
            kotlin.run {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, SPLASH_TIME_OUT)


    }
}
