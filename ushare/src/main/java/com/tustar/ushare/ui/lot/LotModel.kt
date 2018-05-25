package com.tustar.ushare.ui.lot

import android.content.Context
import com.tustar.ushare.data.bean.HttpResult
import com.tustar.ushare.data.bean.User
import com.tustar.ushare.net.RetrofitManager
import com.tustar.ushare.rx.scheduler.SchedulerUtils
import com.tustar.ushare.util.NetUtils
import io.reactivex.Observable

class LotModel {

    fun userList(context: Context, page: Int, pageSize: Int): Observable<HttpResult<MutableList<User>, Any>> {
        var params = mutableMapOf<String, Any>()
        params["page"] = page
        params["page_size"] = pageSize

        return RetrofitManager.service
                .userList().compose(SchedulerUtils.ioToMain())
    }
}