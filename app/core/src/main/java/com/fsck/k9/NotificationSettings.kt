package com.fsck.k9

/**
 * Describes how a notification should behave.
 */
data class NotificationSettings(
    val isRingEnabled: Boolean = false,
    val ringtone: String? = null,
    val light: NotificationLight = NotificationLight.Disabled,
    val isVibrateEnabled: Boolean = false,
    val vibratePattern: VibratePattern = VibratePattern.Default,
    val vibrateTimes: Int = 0
) {
    val vibrationPattern: LongArray
        get() = getVibrationPattern(vibratePattern, vibrateTimes)

    companion object {
        fun getVibrationPattern(vibratePattern: VibratePattern, times: Int): LongArray {
            val selectedPattern = vibratePattern.vibrationPattern
            val repeatedPattern = LongArray(selectedPattern.size * times)
            for (n in 0 until times) {
                System.arraycopy(selectedPattern, 0, repeatedPattern, n * selectedPattern.size, selectedPattern.size)
            }

            // Do not wait before starting the vibration pattern.
            repeatedPattern[0] = 0

            return repeatedPattern
        }
    }
}

enum class VibratePattern(
    /**
     * These are "off, on" patterns, specified in milliseconds.
     */
    val vibrationPattern: LongArray
) {
    Default(vibrationPattern = longArrayOf(300, 200)),
    Pattern1(vibrationPattern = longArrayOf(100, 200)),
    Pattern2(vibrationPattern = longArrayOf(100, 500)),
    Pattern3(vibrationPattern = longArrayOf(200, 200)),
    Pattern4(vibrationPattern = longArrayOf(200, 500)),
    Pattern5(vibrationPattern = longArrayOf(500, 500));

    fun serialize(): Int = when (this) {
        Default -> 0
        Pattern1 -> 1
        Pattern2 -> 2
        Pattern3 -> 3
        Pattern4 -> 4
        Pattern5 -> 5
    }

    companion object {
        fun deserialize(value: Int): VibratePattern = when (value) {
            0 -> Default
            1 -> Pattern1
            2 -> Pattern2
            3 -> Pattern3
            4 -> Pattern4
            5 -> Pattern5
            else -> error("Unknown VibratePattern value: $value")
        }
    }
}
