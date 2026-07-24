package in.maithilart.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.maithilart.common.dto.MaithilErrorResponse;


@RestControllerAdvice
public class MaithilExceptionHandler {
	

	public MaithilExceptionHandler() {
		System.out.println("######### HANDLER LOADED #########");
	}

	@ExceptionHandler(MaithilException.class)
	public ResponseEntity<MaithilErrorResponse> handleMaithilException(MaithilException ex) {

		MaithilErrorResponse response = new MaithilErrorResponse(ex.getErrorCode(), ex.getMessage());

		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MaithilErrorResponse> handleGenericException(Exception ex) {

		MaithilErrorResponse response = new MaithilErrorResponse("INTERNAL_ERROR", "Something went wrong");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
