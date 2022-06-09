import android.content.Context
import android.content.SharedPreferences

class Utils {

    companion object {
        const val KEY_COOKIE: String = "set-cookie"
        private var sharedPreferences: SharedPreferences? = null

        fun setSharedPref(context: Context, key: String, setValue: String) {
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

        fun getSharedPref(context: Context, key: String, defaultValue: String): String? {
            if (sharedPreferences == null) {
                sharedPreferences =
                    context.getSharedPreferences("PREFERENCE_CACHE", Context.MODE_PRIVATE);
            }
            return sharedPreferences?.getString(key, defaultValue);
        }

        fun getSharedPrefCookie(context: Context): String? {
            return getSharedPref(context, KEY_COOKIE, "No cookie is cache")
        }
    }
}