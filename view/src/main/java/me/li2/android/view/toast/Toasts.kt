/*
 * Copyright 2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:JvmName("Toasts")
package me.li2.android.view.toast

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(@StringRes resId: Int) = activity?.toast(resId)
fun Fragment.toast(message: CharSequence) = activity?.toast(message)
fun Fragment.longToast(@StringRes resId: Int) = activity?.longToast(resId)
fun Fragment.longToast(message: CharSequence) = activity?.longToast(message)

fun Context.toast(@StringRes resId: Int): Toast = toast(resId, Toast.LENGTH_SHORT)
fun Context.toast(message: CharSequence): Toast = toast(message, Toast.LENGTH_SHORT)
fun Context.longToast(@StringRes resId: Int): Toast = toast(resId, Toast.LENGTH_LONG)
fun Context.longToast(message: CharSequence): Toast = toast(message, Toast.LENGTH_LONG)

private fun Context.toast(@StringRes resId: Int, duration: Int): Toast =
        toast(resources.getString(resId), duration)

private fun Context.toast(message: CharSequence, duration: Int): Toast = Toast
        .makeText(this, message, duration)
        .apply {
            show()
        }