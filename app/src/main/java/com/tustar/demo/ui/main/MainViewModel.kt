package com.tustar.demo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tustar.demo.R
import com.tustar.demo.ui.hencoder.ch1.PracticeDraw1Activity
import com.tustar.demo.ui.hencoder.ch2.PracticeDraw2Activity
import com.tustar.demo.ui.hencoder.ch3.PracticeDraw3Activity
import com.tustar.demo.ui.hencoder.ch4.PracticeDraw4Activity

class MainViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}

class MainViewModel : ViewModel() {
    val hencoders: LiveData<List<MainItem>> get() = _hencoders
    private var _hencoders = MutableLiveData<List<MainItem>>()

    fun addHencoderDemos() {
        val children = listOf(
                ChildItem(PracticeDraw1Activity::class.java, R.string.hen_practice_draw_1),
                ChildItem(PracticeDraw2Activity::class.java, R.string.hen_practice_draw_2),
                ChildItem(PracticeDraw3Activity::class.java, R.string.hen_practice_draw_3),
                ChildItem(PracticeDraw4Activity::class.java, R.string.hen_practice_draw_4)
        )
        val group = GroupItem(R.string.section_hencoder, children.size)
        val data = mutableListOf<MainItem>().apply {
            add(group)
            addAll(children)
        }
        _hencoders.postValue(data)
    }
//    companion object {
//
//        private val TAG = MainActivity::class.java.simpleName
//        private val HENCODER_DEMOS = listOf(
//                ChildItem(PracticeDraw1Activity::class.java, R.string.hen_practice_draw_1),
//                ChildItem(PracticeDraw2Activity::class.java, R.string.hen_practice_draw_2),
//                ChildItem(PracticeDraw3Activity::class.java, R.string.hen_practice_draw_3),
//                ChildItem(PracticeDraw4Activity::class.java, R.string.hen_practice_draw_4)
//        )
//        private val HENCODER_SECTION = GroupItem(R.string.section_hencoder, HENCODER_DEMOS)
//        //
//        private val BOOK_DEMOS = listOf(
//                ChildItem(FlMainActivity::class.java, R.string.fl_main_title, true),
//                ChildItem(RygMainActivity::class.java, R.string.ryg_main_title, true),
//                ChildItem(QyzMainActivity::class.java, R.string.qyz_main_title, true)
//        )
//        private val BOOK_SECTION = GroupItem(R.string.section_books, BOOK_DEMOS)
//        //
//        private val PROJECT_DEMOS = listOf(
//                ChildItem(FmMainActivity::class.java, R.string.fm_main_title, true),
//                ChildItem(DeskClockActivity::class.java, R.string.deskclock_title),
//                ChildItem(HistoryActivity::class.java, R.string.calculator_title),
//                ChildItem(SignalActivity::class.java, R.string.rf_signal_title),
//                ChildItem(AccountActivity::class.java, R.string.account_title)
//        )
//        private val PROJECT_SECTION = GroupItem(R.string.section_project, PROJECT_DEMOS)
//        //
//        private val ABCS_DEMOS = listOf(
//                ChildItem(ServiceActivity::class.java, R.string.service_title),
//                ChildItem(ProviderActivity::class.java, R.string.provider_title)
//        )
//        private val ABCS_SECTION = GroupItem(R.string.section_abcs, ABCS_DEMOS)
//        //
//        private val WIDGETS_DEMOS = listOf(
//                ChildItem(RecyclerViewActivity::class.java, R.string.recycler_title),
//                ChildItem(DragSortListViewActivity::class.java, R.string.drag_list_title)
//        )
//        private val WIDGETS_SECTION = GroupItem(R.string.section_widgets, WIDGETS_DEMOS)
//        //
//        private val JETPACK_DEMOS = listOf(
//                ChildItem(BookActivity::class.java, R.string.jet_main_title)
//        )
//        private val JETPACK_SECTION = GroupItem(R.string.section_jetpack, JETPACK_DEMOS)
//        //
//        private val ANIMS_DEMOS = listOf(
//                ChildItem(ViewAnimActivity::class.java, R.string.view_anim_title),
//                ChildItem(HideActionBarActivity::class.java, R.string.hide_action_title)
//        )
//        private val ANIMS_SECTION = GroupItem(R.string.section_anims, ANIMS_DEMOS)
//        //
//        private val SCROLLER_DEMOS = listOf(
//                ChildItem(ScrollerActivity::class.java, R.string.scroller_title, true)
//        )
//        private val SCROLLER_SECTION = GroupItem(R.string.section_scroller, SCROLLER_DEMOS)
//        //
//        private val OTHER_DEMOS = listOf(
//                ChildItem(LoaderActivity::class.java, R.string.loader_title)
//        )
//        private val OTHER_SECTION = GroupItem(R.string.section_other, OTHER_DEMOS)
//        //
//        val ITEMS = mutableListOf<MainItem>().apply {
//            //
//            add(HENCODER_SECTION)
//            addAll(HENCODER_DEMOS)
//            //
//            add(BOOK_SECTION)
//            addAll(BOOK_DEMOS)
//            //
//            add(PROJECT_SECTION)
//            addAll(PROJECT_DEMOS)
//            //
//            add(ABCS_SECTION)
//            addAll(ABCS_DEMOS)
//            //
//            add(WIDGETS_SECTION)
//            addAll(WIDGETS_DEMOS)
//            //
//            add(JETPACK_SECTION)
//            addAll(JETPACK_DEMOS)
//            //
//            add(ANIMS_SECTION)
//            addAll(ANIMS_DEMOS)
//            //
//            add(SCROLLER_SECTION)
//            addAll(SCROLLER_DEMOS)
//            //
//            add(OTHER_SECTION)
//            addAll(OTHER_DEMOS)
//        }
//    }
}
