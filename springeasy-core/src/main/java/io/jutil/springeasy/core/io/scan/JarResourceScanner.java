package io.jutil.springeasy.core.io.scan;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.jar.JarFile;

/**
 * @author Jin Zheng
 * @since 2023-06-12
 */
@Slf4j
class JarResourceScanner implements ResourceScanner {
    static final JarResourceScanner INSTANCE = new JarResourceScanner();

    @Override
    @SuppressWarnings("java:S5042")
    public void scan(String base, String path, ResourceHandler... handlers) {
        var loader = Thread.currentThread().getContextClassLoader();
        var jarFile = ResourceUtil.extractJarFile(path);

        try (var jar = new JarFile(jarFile)) {
            var entries = jar.entries();
            while (entries.hasMoreElements()) {
                var entry = entries.nextElement();
                var name = entry.getName();
                if (!name.startsWith(base)) {
                    continue;
                }

                var info = new JarResourceInfo(loader, base, path, entry, jar);
                ResourceUtil.handle(info, handlers);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
