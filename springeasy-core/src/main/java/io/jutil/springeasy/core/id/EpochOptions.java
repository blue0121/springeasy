package io.jutil.springeasy.core.id;

import java.time.LocalDate;
import java.time.ZoneId;

/**
 * @author Jin Zheng
 * @since 2023-05-30
 */
class EpochOptions {
    private LocalDate epoch;
    private int machineId;
    private int machineIdBits;
    private int sequenceBits;

    EpochOptions() {
        this(0);
    }

    EpochOptions(int machineId) {
        this.epoch = LocalDate.of(2025, 1, 1);
        this.machineId = machineId;
        this.machineIdBits = 10;
        this.sequenceBits = 10;
    }


    long getEpochMillis() {
        return epoch.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    LocalDate getEpoch() {
        return epoch;
    }

    EpochOptions setEpoch(LocalDate epoch) {
        this.epoch = epoch;
        return this;
    }

    int getMachineId() {
        return machineId;
    }

    EpochOptions setMachineId(int machineId) {
        this.machineId = machineId;
        return this;
    }

    int getMachineIdBits() {
        return machineIdBits;
    }

    EpochOptions setMachineIdBits(int machineIdBits) {
        this.machineIdBits = machineIdBits;
        return this;
    }

    int getSequenceBits() {
        return sequenceBits;
    }

    EpochOptions setSequenceBits(int sequenceBits) {
        this.sequenceBits = sequenceBits;
        return this;
    }
}
