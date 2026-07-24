package in.maithilart.common.context.provider;

public interface RequestContextProvider {

	String getHeader(String name);

	String getMethod();

	String getRequestUri();

	String getClientIp();

	String getQueryString();

	String getUserAgent();

	String getContentType();

	String getRequestId();
}