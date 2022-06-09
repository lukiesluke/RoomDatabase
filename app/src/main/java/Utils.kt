import android.content.Context
import android.content.SharedPreferences

class Utils {
    companion object {
        private var sharedPreferences: SharedPreferences? = null
        fun setPref(context: Context, key: String, setValue: String) {
            if (sharedPreferences == null) {
                sharedPreferences =
                    context.getSharedPreferences("PREFERENCE_CACHE", Context.MODE_PRIVATE);
            }

            val editor = sharedPreferences?.edit()
            editor?.apply {
                putString(key, setValue)
                apply()
            }
        }

        fun getPref(context: Context, key: String, defaultValue: String): String? {
            if (sharedPreferences == null) {
                sharedPreferences =
                    context.getSharedPreferences("PREFERENCE_CACHE", Context.MODE_PRIVATE);
            }
            return sharedPreferences?.getString(key, defaultValue);
        }
    }
}