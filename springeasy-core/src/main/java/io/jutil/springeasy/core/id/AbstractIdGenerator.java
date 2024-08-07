package io.jutil.springeasy.core.id;

import io.jutil.springeasy.core.util.NumberUtil;
import io.jutil.springeasy.core.util.WaitUtil;

/**
 * @author Jin Zheng
 * @since 2023-10-04
 */
abstract class AbstractIdGenerator<T> implements IdGenerator<T> {
    protected final long sequenceMask;

    protected long sequence = 0L;
    protected long lastTimestamp = 0L;

    AbstractIdGenerator(int sequenceLength) {
        this.sequenceMask = NumberUtil.maskForLong(sequenceLength);
    }

    protected void generateSequence() {
        long timestamp = System.currentTimeMillis();
        this.checkTimestamp(timestamp, lastTimestamp);

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                WaitUtil.sleep(1);
                lastTimestamp = System.currentTimeMillis();
            }
        } else {
            lastTimestamp = timestamp;
            sequence = 0L;
        }
    }

    private void checkTimestamp(long timestamp, long lastTimestamp) {
        if (timestamp >= lastTimestamp) {
            return;
        }

        var interval = timestamp - lastTimestamp;
        if (interval <= 50) {
            WaitUtil.sleep(interval);
        } else {
            throw new IllegalArgumentException(String.format("系统时钟回退，在 %d 毫秒内拒绝生成 ID",
                    interval));
        }
    }

    protected void writeLong(byte[] id, long value, int offset, int size) {
        for (int i = 0; i < size; i++) {
            var shift = (size - i - 1) << 3;
            id[offset + i] = (byte) ((value >>> shift) & 0xff);
        }
    }

    protected void writeTimestamp(byte[] id, long timestamp, int size) {
        for (int i = 0; i < size; i++) {
            var shift = (size - i - 1) << 3;
            id[i] = (byte) ((timestamp >>> shift) & 0xff);
        }
    }

}
