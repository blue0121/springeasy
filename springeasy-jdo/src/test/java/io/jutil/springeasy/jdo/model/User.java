package io.jutil.springeasy.jdo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author Jin Zheng
 * @since 2024-03-11
 */
@Getter
@Setter
@NoArgsConstructor
public class User {
	private Long id;
	private String name;
	private String password;
	private Double score;
	private LocalDate birthday;
	private Date loginTime;
	private Integer state;

}
