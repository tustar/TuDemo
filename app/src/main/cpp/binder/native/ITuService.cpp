//
// Created by tustar on 3/8/22.
//
#include <errno.h>
#include "ITuService.h"

namespace android {
    //使用宏，完成TuService定义
    // IMPLEMENT_META_INTERFACE(TuService, TU_NATIVE_SERVICE);
    // Macro above expands to code below. Doing it by hand so we can log ctor and destructor calls.
    const android::String16 ITuService::descriptor("TuService");

    const android::String16 &ITuService::getInterfaceDescriptor() const {
        return ITuService::descriptor;
    }

    android::sp<ITuService> ITuService::asInterface(const android::sp<android::IBinder> &obj) {
        android::sp<ITuService> intr;
        if (obj != NULL) {
            intr = static_cast<ITuService *>(obj->queryLocalInterface(
                    ITuService::descriptor).get());
            if (intr == NULL) {
                intr = new BpTuService(obj);
            }
        }
        return intr;
    }

    ITuService::ITuService() { LOGD("ITuService::ITuService()"); }

    ITuService::~ITuService() { LOGD("ITuService::~ITuService()"); }
    // End of macro expansion

    //客户端
    BpTuService::BpTuService(const sp <IBinder> &impl) :
            BpInterface<ITuService>(impl) {
    }

    // 实现客户端sayHello方法
    void BpTuService::sayHello() {
        LOGI("BpTuService::sayHello");
        Parcel data, reply;
        data.writeInterfaceToken(ITuService::getInterfaceDescriptor());
        remote()->transact(METHOD_SAY, data, &reply);
        LOGD("get num from BpTuService: %d", reply.readInt32());
    }

    //服务端，接收远程消息，处理onTransact方法
    status_t BnTuService::onTransact(uint_t code, const Parcel &data,
                                     Parcel *reply, uint32_t flags) {
        switch (code) {
            case METHOD_SAY: {    //收到HELLO命令的处理流程
                LOGD("BnTuService:: got the client hello");
                CHECK_INTERFACE(ITuService, data, reply);
                sayHello();
                reply->writeInt32(2015);
                return NO_ERROR;
            }
                break;
            default:
                break;
        }
        return NO_ERROR;
    }

    // 实现服务端sayHello方法
    void BnTuService::sayHello() {
        LOGI("BnTuService::sayHello");
    }
}
