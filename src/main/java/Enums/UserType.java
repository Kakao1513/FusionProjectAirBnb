package Enums;

import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum UserType implements Serializable, Enum {
	ADMIN("ADMIN"),
	HOST("HOST"),
	GUEST("GUEST");

	private final String type;

	UserType(String type) {
		this.type = type;
	}
	public static UserType of(String type){
		return UserType.valueOf(CODE_MAP.get(type));
	}

	private static final Map<String, String> CODE_MAP = Collections.unmodifiableMap(
			Stream.of(values()).collect(Collectors.toMap(UserType::getType, UserType::name))
	);

	@Override
	public String getName() {
		return name();
	}
}
