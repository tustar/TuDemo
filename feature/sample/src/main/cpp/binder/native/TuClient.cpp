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
    // 获取名为"service.myservice"的binder接口
    sp <IBinder> binder = sm->getService(String16(TU_NATIVE_SERVICE));
    // 将binder对象转换为强引用类型的ITuService
    sp <ITuService> cs = interface_cast<ITuService>(binder);
    // 利用binder引用调用远程sayHello()方法
    cs->sayHello();
    return 0;
}