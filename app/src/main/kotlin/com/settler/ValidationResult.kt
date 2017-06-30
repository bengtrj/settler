package com.settler

import android.support.annotation.StringRes

data class ValidationResult(val valid: Boolean, @StringRes val messageResourceId: Int)