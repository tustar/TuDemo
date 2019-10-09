package com.tustar.demo.ui.main

import androidx.annotation.StringRes

data class SectionItem(
        @StringRes val nameResId: Int,
        val contents: List<ContentItem>) : MainItem() {

    override fun getType(): Int = TYPE_SECTION
}