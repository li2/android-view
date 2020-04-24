[![](https://jitpack.io/v/li2/android-view.svg)](https://jitpack.io/#li2/android-view)

## Android View Library

This library icludes bunches of  binding adapters and extension functions for existing views, also some custom views.

```xml
├── LoadingOverlay.kt
├── ViewBindings.kt
├── button
│   ├── CircularAnimatedDrawable.kt
│   └── MaterialLoadingButton.kt
├── image
│   └── ImageBindings.kt
├── list
│   ├── CardPageTransformer.kt
│   ├── CarouselPagerHelper.kt
│   ├── GridSpacingDecoration.kt
│   ├── LinearSpacingDecoration.kt
│   ├── RecyclerViewBindings.kt
│   ├── RecyclerViewItemDragHelper.kt
│   ├── SwipeRefreshLayoutBindings.kt
│   ├── VerticalScrollView.kt
│   ├── ViewPager2AutoScrollHelper.kt
│   └── ViewPager2Ext.kt
├── navigation
│   ├── TabLayoutExtensions.kt
│   ├── ToolbarBindings.kt
│   └── ToolbarExtensions.kt
├── popup
│   ├── Snackbars.kt
│   └── Toasts.kt
├── system
│   ├── KeyboardUtils.kt
│   └── StatusBarUtils.kt
├── text
│   ├── EditTextViewBindings.kt
│   ├── EditTextViewExtensions.kt
│   └── TextViewBindings.kt
└── web
    └── AdvancedWebView.kt
```

## Usage


### Display a list of items with RecyclerView, ViewPager, ScrollView, etc.

`CardPageTransformer`
a card effect transformer for ViewPager2

```kotlin
ViewPager2.setPageTransformer(CardPageTransformer(0.85f, 0.7f))
```

`CarouselPagerHelper`
Provide carousel scroll ability for RecyclerView [ListAdapter] by mocking up the limited dataset with [Int.MAX_VALUE]

```kotlin
 class YourListAdapter : ListAdapter<Item, ItemViewHolder>(DIFF_CALLBACK), CarouselPagerHelper {
     override val carouselDatasetSize: Int
         get() = currentList.size

     override fun onBindViewHolder(viewHolder: TopItemViewHolder, position: Int) {
         val dataPosition = getCarouselDataPosition(position)
         viewHolder.bind(getItem(dataPosition))
     }
     
     override fun getItemCount() = getCarouselDisplaySize()
 }
```
<img width="400" alt="CardPageTransformer+CarouselPagerHelper" src="screenshots/list-CardPageTransformer+CarouselPagerHelper.png">

`ViewPager2AutoScrollHelper`
an interface serve as a plugin to provide the ViewPager auto scroll ability.

```kotlin
class HomeFragment : Fragment(), ViewPager2AutoScrollHelper {
    override val autoScrollViewPager get() = binding.viewPager
    override val viewPagerAutoScrollPeriod = Pair(5L, TimeUnit.SECONDS)
    override var viewPagerAutoScrollTask: Disposable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startViewPagerAutoScrollTask()
    }

    override fun onDestroyView() {
        stopViewPagerAutoScrollTask()
        super.onDestroyView()
    }
}
```

`ViewPager2Ext`
- Ignore pull to refresh
- Show the partial preview of left and right page of ViewPager.

`VerticalScrollView`
A scroll view will only intercept the event if theuser is deliberately scrolling in the Y direction, to make the horizontal scroll view scroll smooth.



## Download

```gradle
implementation 'com.github.li2:android-view:latest_version'
```


## License

```
    Copyright (C) 2020 Weiyi Li

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```