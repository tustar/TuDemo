package com.tustar.demo.ui.custom

import android.content.Context
import androidx.core.graphics.PathParser
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tustar.demo.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import javax.inject.Inject

@HiltViewModel
class SvgMapViewModel @Inject constructor(): ViewModel() {

    val provinces get() = _provinces
    private val _provinces = MutableLiveData<List<ProvinceItem>>()

    //
    private val colors = intArrayOf(-0xdc6429, -0xcf561b, -0x7f340f, -0x4f2808)

    fun parserData(context: Context) {
        Logger.d()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                context.resources.assets.open("china_map.svg").use {
                    val parser = XmlPullParserFactory.newInstance().apply {
                        isNamespaceAware = true
                        setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true)
                    }.newPullParser()
                    parser.setInput(it, "UTF-8")
                    var type = parser.eventType
                    val android = parser.getNamespace("android")
                    val provinces = mutableListOf<ProvinceItem>()
                    //
                    var left = -1F
                    var top = -1F
                    var right = -1F
                    var bottom = -1F
                    //
                    var i = 0
                    while (type != XmlPullParser.END_DOCUMENT) {
                        i++
                        when (type) {
                            XmlPullParser.START_DOCUMENT -> {
                            }
                            XmlPullParser.START_TAG -> {
                                when (parser.name) {
                                    "path" -> {
                                        val pathData = parser.getAttributeValue(
                                            android,
                                            "pathData"
                                        )
                                        val path = PathParser.createPathFromPathData(pathData)
                                        provinces.add(ProvinceItem(path, colors[i % 4]))
                                    }
                                }
                            }
                            XmlPullParser.TEXT -> {

                            }
                            XmlPullParser.END_TAG -> {

                            }
                        }
                        type = parser.next()
                    }
                    _provinces.postValue(provinces)
                }
            }
        }
    }
}