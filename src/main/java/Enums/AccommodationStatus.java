package Enums;

import Container.SerialVersionContainer;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum AccommodationStatus implements Enum, Serializable {

	Waiting("대기중"),
	Refused("거절됨"),
	Confirmed("승인됨");

	@Serial
	private static final long serialVersionUID = SerialVersionContainer.getSerialVersionUID();

	private final String type;

	AccommodationStatus(String status){
		this.type = status;
	}

	public static AccommodationStatus of(String type){
		return AccommodationStatus.valueOf(STATUS_MAP.get(type));
	}

	private static final Map<String, String> STATUS_MAP = Collections.unmodifiableMap(
			Stream.of(values()).collect(Collectors.toMap(AccommodationStatus::getType, AccommodationStatus::name))
	);

	@Override
	public String getName() {
		return name();
	}

}
