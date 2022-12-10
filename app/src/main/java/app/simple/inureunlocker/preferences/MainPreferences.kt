package app.simple.inureunlocker.preferences

object MainPreferences {

    const val rateState = "rate_state"

    //----------------------------------------------------------------------------------------------//

    fun getRateState(): Boolean {
        return SharedPreferences.getSharedPreferences().getBoolean(rateState, false)
    }

    fun setRateState(state: Boolean) {
        SharedPreferences.getSharedPreferences().edit().putBoolean(rateState, state).apply()
    }
}