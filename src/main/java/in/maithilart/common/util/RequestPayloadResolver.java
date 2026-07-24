package in.maithilart.common.util;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import in.maithilart.common.constants.MaithilConstants;
import in.maithilart.common.exception.MaithilException;

@Component
public class RequestPayloadResolver {

	public Object resolve(ProceedingJoinPoint joinPoint) {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();

		Method method = signature.getMethod();

		Parameter[] parameters = method.getParameters();

		Object[] arguments = joinPoint.getArgs();

		for (int i = 0; i < parameters.length; i++) {

			Parameter parameter = parameters[i];

			if (parameter.isAnnotationPresent(RequestBody.class)) {
				return arguments[i];
			}

			if (parameter.isAnnotationPresent(RequestPart.class)) {

				if (!(arguments[i] instanceof MultipartFile)) {
					return arguments[i];
				}
			}
		}

		throw new MaithilException(MaithilConstants.INVALID_REQUEST, "@Idempotent requires a request payload.");
	}
}