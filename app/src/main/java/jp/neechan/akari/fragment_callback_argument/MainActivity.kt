package jp.neechan.akari.fragment_callback_argument

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import jp.neechan.akari.fragment_callback_argument.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!wasFragmentAdded) {
            showSafeFragment()
            wasFragmentAdded = true
        }
    }

    private fun showUnsafeFragment() {
        val unsafeFragment = UnsafeFragment.newInstance("Hello from MainActivity!") {
            Toast.makeText(this, "Hi!", Toast.LENGTH_SHORT).show()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, unsafeFragment)
            .commit()
    }

    private fun showSafeFragment() {
        val safeFragment = SafeFragment.newInstance("Hello from MainActivity!") {
            Toast.makeText(this, "Hi!", Toast.LENGTH_SHORT).show()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, safeFragment)
            .commit()
    }

    companion object {
        private var wasFragmentAdded = false
    }
}
