package io.jutil.springeasy.core.io.scan;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-06-12
 */
class FileHandler implements ResourceHandler {
    final List<ResourceInfo> infoList = new ArrayList<>();
    final List<String> nameList = new ArrayList<>();
    final List<Class<?>> classList = new ArrayList<>();

    @Override
    public boolean accepted(ResourceInfo info) {
        return true;
    }

    @Override
    public Result handle(ResourceInfo info) {
        infoList.add(info);
        nameList.add(info.getFileName());

        var clazz = info.resolveClass();
        if (clazz != null) {
            classList.add(clazz);
        }
        return Result.CONTINUE;
    }
}
