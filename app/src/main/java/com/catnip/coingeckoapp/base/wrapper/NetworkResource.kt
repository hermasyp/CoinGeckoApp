package com.catnip.coingeckoapp.base.wrapper

import java.lang.Exception

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

sealed class NetworkResource<T>(
    val data: T? = null,
    val message: String? = null,
    val exception: Exception? = null,
) {
    class Success<T>(data: T) : NetworkResource<T>(data)
    class Loading<T>(data: T? = null) : NetworkResource<T>(data)
    class Error<T>(exception: Exception?, data: T? = null) : NetworkResource<T>(data, exception = exception)
}
