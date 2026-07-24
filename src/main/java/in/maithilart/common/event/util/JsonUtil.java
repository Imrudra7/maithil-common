package in.maithilart.common.event.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

	/**
	 * 🔥 अल्टीमेट डायनेमिक कनवर्टर: सिर्फ क्लास का नाम दो (चाहे सिंगल ऑब्जेक्ट
	 * चाहिए हो या लिस्ट), ये खुद हैंडल करेगा!
	 */
    public static <T> T convert(Object fromValue, Class<?> targetClass, Class<?>... parameterClasses) {
        if (fromValue == null) {
            return null;
        }
        
        JavaType targetType;
        if (parameterClasses.length > 0) {
            // अगर पैरामीटर क्लासेज दी हैं (जैसे List.class, ProductVariantResponse.class)
            targetType = objectMapper.getTypeFactory().constructParametricType(targetClass, parameterClasses);
        } else {
            // अगर सिर्फ सिंगल क्लास दी है (जैसे ProductResponse.class)
            targetType = objectMapper.getTypeFactory().constructType(targetClass);
        }
        
        return objectMapper.convertValue(fromValue, targetType);
    }
}