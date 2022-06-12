package academy.bangkit.c22.px441.kalkal

import com.google.android.material.textfield.TextInputEditText
import kotlin.math.abs
import kotlin.math.roundToInt

fun Float.twoPreciseAbs() = abs((this * 100.0).roundToInt() / 100.0)
fun TextInputEditText.toInt() = Integer.parseInt(this.text.toString())