package com.ara.base.domain.message;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.ara.common.util.GsonUtils;

import java.lang.reflect.Type;

/**
 * Created by XieXin on 2019/1/10.
 */
@Route(path = "/constant/json")
public class JsonServiceImpl implements SerializationService {
    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return GsonUtils.toBean(input, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return GsonUtils.toString(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return GsonUtils.toObject(input, clazz);
    }

    @Override
    public void init(Context context) {

    }
}
