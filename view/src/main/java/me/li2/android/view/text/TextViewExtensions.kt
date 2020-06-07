/*
 * Created by weiyi on 2019-10-28.
 * https://github.com/li2
 */
@file:JvmName("EditTextViewExtensions")
@file:Suppress("unused")
package me.li2.android.view.text

import android.widget.EditText
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable
import me.li2.android.common.number.orZero
import me.li2.android.common.rx.mapToString
import java.util.concurrent.TimeUnit

fun EditText.errorCheckingTextChanges(
        textInputLayout: TextInputLayout,
        @StringRes errorMessageId: Int,
        isValid: (String) -> Boolean): Observable<String> {
    return textChanges().mapToString().doOnNext { input ->
        if (input.isNotEmpty()) {
            textInputLayout.error = if (isValid(input)) null else textInputLayout.context.getString(errorMessageId)
        }
    }
}

/**
 * Return query text changes observable.
 *
 * - filter: to filter undesired text like blank text to avoid unnecessary API call.
 * - debounce: to ignore the previous items in the given time and only emit the last one,
 *             to avoid too much API calls.
 * - distinctUntilChanged: to avoid the duplicate API call.
 */
fun EditText.queryTextChanges(): Observable<String> {
    return textChanges()
            .map { it.toString() }
            .filter { it.isNotBlank() }
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
}

/**
 * Text changes observable which only be triggered by user input.
 * To make it works, call [setTextProgrammatically] whenever change text programmatically.
 *
 * https://stackoverflow.com/a/25751207/2722270
 */
fun EditText.userTextChanges(): Observable<CharSequence> {
    return Observable.create { emitter ->
        textChanges().skipInitialValue().subscribe { text ->
            if (tag == null) {
                // means the change is from user, then emitter it
                emitter.onNext(text)
            }
        }
    }
}

fun EditText.setTextProgrammatically(text: CharSequence?) {
    tag = "the arbitrary const tag is used to distinguish the textChanges() is by user or code"
    setText(text)
    setSelection(text?.length.orZero())
    tag = null
}

fun TextInputLayout.endIconClicks(): Observable<Unit> {
    return Observable.create { emitter ->
        setEndIconOnClickListener {
            emitter.onNext(Unit)
        }
    }
}