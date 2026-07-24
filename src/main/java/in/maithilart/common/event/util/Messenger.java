package in.maithilart.common.event.util;

import java.util.Base64;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import in.maithilart.common.constants.MaithilConstants;
import in.maithilart.common.context.provider.CommunicatorSecretProvider;
import in.maithilart.common.exception.MaithilException;

@Component
public class Messenger {

	private static final String ALGO = "HmacSHA256";
	private final ObjectMapper mapper = new ObjectMapper();
	
	private final CommunicatorSecretProvider communicatorSecretProvider;

	public Messenger(CommunicatorSecretProvider communicatorSecretProvider) {
		this.communicatorSecretProvider = communicatorSecretProvider;
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	public String pack(Object data) {
		try {
			return mapper.writeValueAsString(data);
		} catch (Exception e) {
			throw new MaithilException(MaithilConstants.ERROR,"Thaila pack karne mein fatt gaya!", e);
		}
	}

	/* 2. THAILA (JSON) SE DATA NIKALO (AS MAP) */
	@SuppressWarnings("unchecked")
	public Map<String, Object> unpack(String json) {
		try {
			return mapper.readValue(json, Map.class);
		} catch (Exception e) {
			throw new MaithilException(MaithilConstants.ERROR,"Thaila kholne mein panga ho gaya!", e);
		}
	}

	/* 3. SEAL (SIGNATURE) LAGAO */
	public String sign(String data) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(communicatorSecretProvider.getCommunicatorSecret().getBytes(),
					ALGO);
			Mac mac = Mac.getInstance(ALGO);
			mac.init(keySpec);
			byte[] hash = mac.doFinal(data.getBytes());
			return Base64.getEncoder().encodeToString(hash);
		} catch (Exception e) {
			throw new MaithilException(MaithilConstants.ERROR,"Seal (Signature) nahi lag payi! Secret check karo.", e);
		}
	}

	/* 4. SEAL CHECK KARO (VERIFY) */
	public boolean isSealValid(String data, String signature) {
		if (signature == null)
			return false;
		return sign(data).equals(signature);
	}

}
