
package in.maithilart.common.idempotency;

/**
 *  Helper method for JoinPoint .proceed
 */
public interface IdempotentAction {

	Object execute() throws Throwable;
}
