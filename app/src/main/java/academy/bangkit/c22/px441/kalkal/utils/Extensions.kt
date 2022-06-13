package academy.bangkit.c22.px441.kalkal

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.abs
import kotlin.math.roundToInt

fun Float.twoPreciseAbs() = abs((this * 100.0).roundToInt() / 100.0)
fun TextInputEditText.toInt() = Integer.parseInt(this.text.toString())
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")