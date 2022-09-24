//
// Created by tustar on 3/8/22.
//
#include <binder/IServiceManager.h>
#include <binder/IPCThreadState.h>
#include "ITuService.h"

using namespace android;

int main() {
    // 获取service manager引用
    sp <IServiceManager> sm = defaultServiceManager();
    // 注册名为TU_NATIVE_SERVICE的服务到service manager
    sm->addService(String16(TU_NATIVE_SERVICE), new BnTuService());
    // 启动线程池
    ProcessState::self()->startThreadPool();
    // 把主线程加入线程池
    IPCThreadState::self()->joinThreadPool();
    return 0;
}