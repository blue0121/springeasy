package io.jutil.springeasy.core.codec;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2023-01-08
 */
@Getter
@Setter
@NoArgsConstructor
public class DateTypeObject implements ExternalSerializable {
	private Date valDate;
	private LocalDateTime valLocalDateTime;
	private Instant valInstant;

	@Override
	public void encode(Encoder encoder) {
		encoder.writeDate(valDate);
		encoder.writeLocalDateTime(valLocalDateTime);
		encoder.writeInstant(valInstant);
	}

	@Override
	public void decode(Decoder decoder) {
		this.valDate = decoder.readDate();
		this.valLocalDateTime = decoder.readLocalDateTime();
		this.valInstant = decoder.readInstant();
	}
}
