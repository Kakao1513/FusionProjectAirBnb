package persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class InsertBoardDTO {
	private String title;
	private String writer;
	private String contents;
	private LocalDateTime regDate;
	private int hit;
}
