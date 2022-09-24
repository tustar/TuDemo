//
// Created by tustar on 3/8/22.
//

#ifndef TUDEMO_ITUSERVICE_H
#define TUDEMO_ITUSERVICE_H

#include <android/log.h>
#include <binder/IInterface.h>
#include <binder/Parcel.h>
#include <binder/IPCThreadState.h>
#include <binder/IServiceManager.h>

#ifdef TAG
#undef TAG
#endif
#define TAG "TuNativeService"

#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE,TAG ,__VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__)

#define TU_NATIVE_SERVICE "service.tu"

namespace android {
    //定义命令字段
    enum {
        METHOD_SAY = 1,
    };

    class ITuService : public IInterface {
    public:
        DECLARE_META_INTERFACE(TuService); //使用宏，申明TuService
        virtual void sayHello() = 0; //定义方法
    };

    // 申明客户端BpTuService
    class BpTuService : public BpInterface<ITuService> {
    public:
        BpTuService(const sp <IBinder> &impl);

        virtual void sayHello();
    };

    // 申明服务端BnTuService
    class BnTuService : public BnInterface<ITuService> {
    public:
        virtual status_t
        onTransact(uint32_t code, const Parcel &data, Parcel *reply, uint32_t flags = 0);

        virtual void sayHello();
    };
}
#endif //TUDEMO_ITUSERVICE_H
