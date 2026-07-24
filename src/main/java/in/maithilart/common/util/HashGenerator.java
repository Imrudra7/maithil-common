package in.maithilart.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HashGenerator {

	private final ObjectMapper objectMapper;

	public HashGenerator(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public String generate(Object body, String algo) {

		try {

			String json = objectMapper.writeValueAsString(body);

			MessageDigest digest = MessageDigest.getInstance(algo);

			byte[] hash = digest.digest(json.getBytes(StandardCharsets.UTF_8));

			return HexFormat.of().formatHex(hash);

		} catch (Exception ex) {

			throw new IllegalStateException(ex);

		}
	}
}