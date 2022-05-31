package com.catnip.coingeckoapp.utils

import android.os.Build
import android.text.Html
import android.widget.TextView

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
fun TextView.textFromHtml(htmlString: String?) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(htmlString)
    }
}