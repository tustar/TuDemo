package com.tustar.demo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tustar.annotation.GROUP_HEN_ID
import com.tustar.annotation.RowGroup
import com.tustar.demo.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val DATABASE_NAME = "demo.db"
const val HEN_ID = GROUP_HEN_ID

@Database(entities = [Group::class, Demo::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class DemoDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao
    abstract fun demoDao(): DemoDao

    companion object {
        @Volatile
        private var instance: DemoDatabase? = null

        fun getInstance(context: Context): DemoDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): DemoDatabase {
            return Room.databaseBuilder(context, DemoDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            GlobalScope.launch(Dispatchers.IO) {
                                initDatabase(context)
                            }
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                        }

                        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                            super.onDestructiveMigration(db)
                        }
                    })
                    .build()
        }

        private suspend fun initDatabase(context: Context) {
            val database = DemoDatabase.getInstance(context)
//            val groups = listOf(
//                    Group(id =HEN_ID, name = R.string.group_hencoder)
//                    Group(id = BOOK_ID, name = R.string.group_book_demo)
//                    Group(id = 3, name = R.string.group_component),
//                    Group(id = 4, name = R.string.group_survey),
//                    Group(id = 5, name = R.string.group_anim),
//                    Group(id = 6, name = R.string.group_widget),
//                    Group(id = 7, name = R.string.group_custom),
//                    Group(id = 8, name = R.string.group_jetpack),
//                    Group(id = 9, name = R.string.group_other)
//            )
//            val hencoder = listOf(
//                    Demo(groupId = HEN_ID,
//                            name = R.string.hen_practice_draw_1,
//                            actionId = R.id.action_main_to_draw1),
//                    Demo(groupId = HEN_ID,
//                            name = R.string.hen_practice_draw_2,
//                            actionId = R.id.action_main_to_draw2),
//                    Demo(groupId = HEN_ID,
//                            name = R.string.hen_practice_draw_3,
//                            actionId = R.id.action_main_to_draw3),
//                    Demo(groupId = HEN_ID,
//                            name = R.string.hen_practice_draw_4,
//                            actionId = R.id.action_main_to_draw4)
//            )
//            val books = listOf(
//                    Demo(groupId = BOOK_ID,
//                            name = R.string.fl_main_title,
//                            actionId = R.id.action_main_to_fl,
//                            isMenu = true),
//                    Demo(groupId = BOOK_ID,
//                            name = R.string.ryg_main_title,
//                            actionId = R.id.action_main_to_ryg,
//                            isMenu = true),
//                    Demo(groupId = BOOK_ID,
//                            name = R.string.qyz_main_title,
//                            actionId = R.id.action_main_to_qyz,
//                            isMenu = true)
//            )
            database.groupDao().insertAll(generateGroups())
            database.demoDao().apply {
                insertAll(generateDemos())
//                insertAll(books)
            }


            //    val hencoderDemos: LiveData<List<MainItem>> = addHencoderDemos()
//    private fun addHencoderDemos(): MutableLiveData<List<MainItem>> {
//        val children = listOf(
//                ChildItem(R.string.hen_practice_draw_GROUP_HENCODER, MainFragmentDirections.actionMainToDrawGROUP_HENCODER()),
//                ChildItem(R.string.hen_practice_draw_2, MainFragmentDirections.actionMainToDraw2()),
//                ChildItem(R.string.hen_practice_draw_3, MainFragmentDirections.actionMainToDraw3()),
//                ChildItem(R.string.hen_practice_draw_4, MainFragmentDirections.actionMainToDraw4())
//        )
//        val group = GroupItem(R.string.group_hencoder, children.size)
//        val data = mutableListOf<MainItem>().apply {
//            add(group)
//            addAll(children)
//        }
//        return MutableLiveData(data)
//    }
//
//    val bookDemos: LiveData<List<MainItem>> = addBookDemos()
//    private fun addBookDemos(): MutableLiveData<List<MainItem>> {
//        val children = listOf(
//                ChildItem(R.string.fl_main_title, MainFragmentDirections.actionMainToFl(),
//                        true),
//                ChildItem(R.string.ryg_main_title, MainFragmentDirections.actionMainToRyg(),
//                        true),
//                ChildItem(R.string.qyz_main_title, MainFragmentDirections.actionMainToQyz(),
//                        true)
//        )
//        val group = GroupItem(R.string.group_book_demo, children.size)
//        val data = mutableListOf<MainItem>().apply {
//            add(group)
//            addAll(children)
//        }
//        return MutableLiveData(data)
//    }
//
//    val componentDemos: LiveData<List<MainItem>> = addComponentDemos()
//    private fun addComponentDemos(): MutableLiveData<List<MainItem>> {
//        val children = listOf(
//                ChildItem(R.string.demo_service_title, MainFragmentDirections.actionMainToService()),
//                ChildItem(R.string.history_provider_title, MainFragmentDirections.actionMainToProvider())
//
//        )
//        val group = GroupItem(R.string.group_component, children.size)
//        val data = mutableListOf<MainItem>().apply {
//            add(group)
//            addAll(children)
//        }
//        return MutableLiveData(data)
//    }
//
//    val surveyDemos: LiveData<List<MainItem>> = addSurveyDemos()
//    private fun addSurveyDemos(): MutableLiveData<List<MainItem>> {
//        val children = listOf(
//                ChildItem(R.string.fm_main_title, MainFragmentDirections.actionMainToFm(),
//                        true)
//        )
//        val group = GroupItem(R.string.group_survey, children.size)
//        val data = mutableListOf<MainItem>().apply {
//            add(group)
//            addAll(children)
//        }
//        return MutableLiveData(data)
//    }
//
//    val animDemos: LiveData<List<MainItem>> = addAnimDemos()
//    private fun addAnimDemos(): MutableLiveData<List<MainItem>> {
//        val children = listOf(
//                ChildItem(R.string.fm_main_title, MainFragmentDirections.actionMainToFm(),
//                        true)
//        )
//        val group = GroupItem(R.string.group_anim, children.size)
//        val data = mutableListOf<MainItem>().apply {
//            add(group)
//            addAll(children)
//        }
//        return MutableLiveData(data)
//    }
//
//    val widgetDemos: LiveData<List<MainItem>> = addWidgetDemos()
//    private fun addWidgetDemos(): MutableLiveData<List<MainItem>> {
//        val children = listOf(
//                ChildItem(R.string.fm_main_title, MainFragmentDirections.actionMainToFm(),
//                        true)
//        )
//        val group = GroupItem(R.string.group_widget, children.size)
//        val data = mutableListOf<MainItem>().apply {
//            add(group)
//            addAll(children)
//        }
//        return MutableLiveData(data)
//    }
//
//    val customDemos: LiveData<List<MainItem>> = addCustomDemos()
//    private fun addCustomDemos(): MutableLiveData<List<MainItem>> {
//        val children = listOf(
//                ChildItem(R.string.fm_main_title, MainFragmentDirections.actionMainToFm(),
//                        true)
//        )
//        val group = GroupItem(R.string.group_custom, children.size)
//        val data = mutableListOf<MainItem>().apply {
//            add(group)
//            addAll(children)
//        }
//        return MutableLiveData(data)
//    }
//
//    val jetpackDemos: LiveData<List<MainItem>> = addJetpackDemos()
//    private fun addJetpackDemos(): MutableLiveData<List<MainItem>> {
//        val children = listOf(
//                ChildItem(R.string.fm_main_title, MainFragmentDirections.actionMainToFm(),
//                        true)
//        )
//        val group = GroupItem(R.string.group_jetpack, children.size)
//        val data = mutableListOf<MainItem>().apply {
//            add(group)
//            addAll(children)
//        }
//        return MutableLiveData(data)
//    }
//
//    val otherDemos: LiveData<List<MainItem>> = addOtherDemos()
//    private fun addOtherDemos(): MutableLiveData<List<MainItem>> {
//        val children = listOf(
//                ChildItem(R.string.fm_main_title, MainFragmentDirections.actionMainToFm(),
//                        true)
//        )
//        val group = GroupItem(R.string.group_other, children.size)
//        val data = mutableListOf<MainItem>().apply {
//            add(group)
//            addAll(children)
//        }
//        return MutableLiveData(data)
//    }

//    <string name="group_anim">动画</string>
//    <string name="group_widgets">新控件(MotionLayout)</string>
//    <string name="group_custom">自定义View</string>
//    <string name="group_jetpack">Jetpack</string>
//    <string name="group_other">其他</string>

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
        }
    }
}