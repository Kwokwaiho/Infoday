package comp4097.comp.hkbu.edu.hk.infoday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import comp4097.comp.hkbu.edu.hk.infoday.ui.signup.SignupFragment

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SignupFragment.newInstance())
                .commitNow()
        }
    }
}