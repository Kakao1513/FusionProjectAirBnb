package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class BoardDTO implements DTO{
	private long id;
	private String title;
	private String writer;
	private String contents;
	private LocalDateTime regDate;
	private int hit;

}
