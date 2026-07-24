
package in.maithilart.common.constants;

import java.util.UUID;

public final class MaithilConstants {

	private MaithilConstants() {
	}

	// Event Types
	public static final String USER_REGISTERED_EVENT = "USER_REGISTERED";
	public static final String PRODUCT_CREATED_EVENT = "PRODUCT_CREATED";
	public static final String ORDER_CREATED = "ORDER_CREATED";
	public static final String ORDER_CANCELLED = "ORDER_CANCELLED";

	public static final String PAYMENT_SUCCESS = "PAYMENT_SUCCESS";
	public static final String PAYMENT_FAILED = "PAYMENT_FAILED";

	public static final String USER_REGISTERED = "USER_REGISTERED";

	// Statuses
	public static final String PENDING_STATUS = "PENDING";
	public static final String PROCESSED_STATUS = "PROCESSED";
	public static final String FAILED_STATUS = "FAILED";

	// Table
	public static final String COMMUNICATOR_TABLE = "AUTH.COMMUNICATOR";

	// Error
	public static final String DATA_NOT_FOUND = "DATA_NOT_FOUND";
	public static final String DATA_TAMPERED = "TAMPERED_DATA";
	public static final String INVALID_DATA = "INVALID_DATA";
	public static final String NULL_DATA = "NULL_DATA";
	public static final String COMMUNICATOR_ERROR = "COMMUNICATOR_ERROR";
	public static final String EVENT_PROCESSING_ERROR = "EVENT_PROCESSING_ERROR";
	public static final String DATABASE_ERROR = "DATABASE_ERROR";
	public static final String INTERNAL_ERROR = "INTERNAL_ERROR";

	// ==========================
	// Response Codes
	// ==========================

	public static final String PROFILE_FETCHED = "PROFILE_FETCHED";
	public static final String PROFILE_NOT_FOUND = "PROFILE_NOT_FOUND";
	public static final String PROFILE_UPDATED = "PROFILE_UPDATED";
	public static final String USER_FETCHED = "USER_FETCHED";
	public static final String USER_CREATED = "USER_CREATED";
	public static final String PRODUCT_CREATED = "PRODUCT_CREATED";
	public static final String PRODUCT_FETCHED = "PRODUCT_FETCHED";
	public static final String PRODUCTS_FETCHED = "PRODUCTS_FETCHED";
	public static final String PRODUCT_UPDATED = "PRODUCT_UPDATED";
	public static final String PRODUCT_DELETED = "PRODUCT_DELETED";

	// ==========================
	// Response Messages
	// ==========================

	public static final String PROFILE_FETCHED_MESSAGE = "Profile fetched successfully";

	public static final String PROFILE_NOT_FOUND_MESSAGE = "User profile not found";

	public static final String PROFILE_UPDATED_MESSAGE = "Profile updated successfully";

	public static final String USER_FETCHED_MESSAGE = "User fetched successfully";

	public static final String USER_CREATED_MESSAGE = "User registered successfully";

	public static final String PRODUCT_CREATED_MESSAGE = "Product created successfully";

	public static final String PRODUCT_UPDATED_MESSAGE = "Product updated successfully";

	public static final String PRODUCT_DELETED_MESSAGE = "Product deleted successfully";

	public static final String PRODUCT_FETCHED_MESSAGE = "Product fetched successfully";

	public static final String PRODUCTS_FETCHED_MESSAGE = "Products fetched successfully";

	// ==========================
	// Notification Keys
	// ==========================

	public static final String TYPE = "type";
	public static final String ACTION = "action";
	public static final String TITLE = "title";
	public static final String DURATION = "duration";
	public static final String REDIRECT_URL = "redirectUrl";

	// ==========================
	// Notification Types
	// ==========================

	public static final String SUCCESS = "SUCCESS";
	public static final String INFO = "INFO";
	public static final String WARNING = "WARNING";
	public static final String ERROR = "ERROR";
	public static final String FATAL = "FATAL";

	// ==========================
	// Notification Actions
	// ==========================

	public static final String SHOW_TOAST = "SHOW_TOAST";
	public static final String SHOW_MODAL = "SHOW_MODAL";
	public static final String REDIRECT = "REDIRECT";
	public static final String REFRESH_PAGE = "REFRESH_PAGE";

	public static final String CATEGORY_CREATED = "CATEGORY_CREATED";
	public static final String CATEGORY_CREATED_MESSAGE = "Category created successfully";

	public static final String CATEGORY_TREE_FETCHED = "CATEGORY_TREE_FETCHED";
	public static final String CATEGORY_TREE_FETCHED_MESSAGE = "Category tree fetched successfully";

	public static final String CATEGORIES_FETCHED = "CATEGORIES_FETCHED";
	public static final String CATEGORIES_FETCHED_MESSAGE = "Categories fetched successfully";

	public static final String MERCHANT_CREATED = "MERCHANT_CREATED";
	public static final String MERCHANT_CREATED_MESSAGE = "Merchant created successfully";

	public static final String MERCHANT_FETCHED = "MERCHANT_FETCHED";
	public static final String MERCHANT_FETCHED_MESSAGE = "Merchant fetched successfully";

	public static final String MERCHANTS_FETCHED = "MERCHANTS_FETCHED";
	public static final String MERCHANTS_FETCHED_MESSAGE = "Merchants fetched successfully";
	public static final String USER_PROFILE_FETCHED = "USER_PROFILE_FETCHED";

	public static final String EVENT_PUBLISH_QUERY = """
			INSERT INTO communicator.event
			(
			    event_id,
			    event_type,
			    entity_type,
			    entity_id,
			    payload,
			    metadata,
			    payload_version,
			    idempotency_key,
			    publisher,
			    correlation_id,
			    status,
			    created_at
			)
			VALUES
			(
			    ?, ?, ?, ?, ?::jsonb,?::jsonb,
			    ?, ?, ?, ?, ?, ?
			)
			""";
	public static final String PENDING_DISPATCH = "PENDING_DISPATCH";
	public static final int PAYLOAD_VERSION = 1;

	public static final String POLL_PENDING_DELIVERY_QUERY = """
			SELECT d.delivery_id, d.event_id, d.status, d.attempt_count,
			       d.max_retry, d.next_retry_at, d.created_at,
			       e.event_type, e.entity_type, e.entity_id, e.payload, e.metadata
			FROM communicator.delivery d
			JOIN communicator.subscription s ON d.subscription_id = s.subscription_id
			JOIN communicator.event e ON d.event_id = e.event_id
			WHERE s.subscriber_name = ?
			  AND d.status = 'PENDING'
			  AND d.next_retry_at <= now()
			ORDER BY d.created_at ASC
			LIMIT ?
			FOR UPDATE SKIP LOCKED
			""";
	public static final String POLL_PENDING_NOTIFICATION_DELIVERY_QUERY = """
			 SELECT
			    d.delivery_id,
			    d.notification_id,
			    d.status,
			    d.attempt_count,
			    d.max_retry,
			    d.next_retry_at,
			    d.last_attempted_at,
			    d.processed_at,
			    d.error_message,
			    d.created_at
			FROM notification.notification_delivery d
			WHERE d.status = 'PENDING'
			  AND d.next_retry_at <= ?
			ORDER BY d.created_at ASC
			LIMIT ?
			FOR UPDATE SKIP LOCKED
			            """;

	public static final String MARK_DELIVERY_IN_PROGRESS_QUERY = """
			UPDATE communicator.delivery
			SET status = 'IN_PROGRESS', last_attempted_at = ?
			WHERE delivery_id = ?
			""";
	public static final String MARK_NOTIFICATION_DELIVERY_IN_PROGRESS_QUERY = """
			UPDATE notification.notification_delivery
			SET status = 'IN_PROGRESS', last_attempted_at = ?
			WHERE delivery_id = ?
			""";
	public static final String MARK_DELIVERY_SUCCESS_QUERY = """
			UPDATE communicator.delivery
			SET status = 'SUCCESS', processed_at = ?, error_message = NULL
			WHERE delivery_id = ?
			""";
	public static final String MARK_NOTIFICATION_DELIVERY_SUCCESS_QUERY = """
			UPDATE notification.notification_delivery
			SET status = 'SUCCESS', processed_at = ?, error_message = NULL, provider_success_msg_id = ?
			WHERE delivery_id = ?
			""";

	public static final String MARK_DELIVERY_FAILED_QUERY = """
			UPDATE communicator.delivery
			SET
			    status = CASE
			        WHEN attempt_count + 1 >= max_retry THEN 'DEAD_LETTER'
			        ELSE 'PENDING'
			    END,
			    attempt_count = attempt_count + 1,
			    error_message = ?,
			    next_retry_at = CASE
			        WHEN attempt_count + 1 >= max_retry THEN next_retry_at
			        ELSE ?
			    END
			WHERE delivery_id = ?
			""";
	public static final String MARK_NOTIFICATION_DELIVERY_FAILED_QUERY = """
			UPDATE notification.notification_delivery
			SET
			    status = CASE
			        WHEN attempt_count + 1 >= max_retry THEN 'DEAD_LETTER'
			        ELSE 'PENDING'
			    END,
			    attempt_count = attempt_count + 1,
			    error_message = ?,
			    next_retry_at = CASE
			        WHEN attempt_count + 1 >= max_retry THEN next_retry_at
			        ELSE ?
			    END,
			    last_attempted_at = CURRENT_TIMESTAMP
			WHERE delivery_id = ?
			""";
	public static final String CART_INCREASE_ITEM = "CART_INCREASE_ITEM";
	public static final String CART_DECREASE_ITEM = "CART_DECREASE_ITEM";

	// ==========================
	// CART STATUS
	// ==========================

	public static final String CART_STATUS_ACTIVE = "ACTIVE";

	public static final String CART_STATUS_CHECKED_OUT = "CHECKED_OUT";

	public static final String CART_STATUS_ABANDONED = "ABANDONED";

	public static final String CART_STATUS_EXPIRED = "EXPIRED";

	// ==========================
	// EVENT TYPES
	// ==========================

	public static final String ITEM_ADDED_TO_CART = "ITEM_ADDED_TO_CART";

	public static final String ITEM_REMOVED_FROM_CART = "ITEM_REMOVED_FROM_CART";

	public static final String ITEM_QUANTITY_UPDATED = "ITEM_QUANTITY_UPDATED";

	public static final String CART_CLEARED = "CART_CLEARED";

	public static final String CART_CHECKED_OUT = "CART_CHECKED_OUT";

	public static final String CART_ABANDONED = "CART_ABANDONED";

	// ==========================
	// SUCCESS CODES
	// ==========================

	public static final String CART_FETCHED = "CART_FETCHED";

	public static final String CART_CREATED = "CART_CREATED";

	public static final String ITEM_ADDED = "ITEM_ADDED";

	public static final String ITEM_UPDATED = "ITEM_UPDATED";

	public static final String ITEM_REMOVED = "ITEM_REMOVED";

	public static final String CART_CLEARED_SUCCESS = "CART_CLEARED_SUCCESS";

	// ==========================
	// ERROR CODES
	// ==========================

	public static final String CART_NOT_FOUND = "CART_NOT_FOUND";

	public static final String CART_ITEM_NOT_FOUND = "CART_ITEM_NOT_FOUND";

	public static final String PRODUCT_NOT_FOUND = "PRODUCT_NOT_FOUND";

	public static final String VARIANT_NOT_FOUND = "VARIANT_NOT_FOUND";

	public static final String INVALID_QUANTITY = "INVALID_QUANTITY";

	public static final String PRODUCT_INACTIVE = "PRODUCT_INACTIVE";

	public static final String INSUFFICIENT_STOCK = "INSUFFICIENT_STOCK";

	// ==========================
	// RESPONSE MESSAGES
	// ==========================

	public static final String CART_FETCHED_MESSAGE = "Cart fetched successfully";
	public static final String CART_ITEM_FETCH_FAIL_MESSAGE = "Cart item not found";

	public static final String CART_CREATED_MESSAGE = "Cart created successfully";

	public static final String ITEM_ADDED_MESSAGE = "Item added to cart successfully";

	public static final String ITEM_UPDATED_MESSAGE = "Cart item updated successfully";
	public static final String ITEM_UPDATE_FAIL_MESSAGE = "Cart item updatation failed";

	public static final String ITEM_REMOVED_MESSAGE = "Cart item removed successfully";
	public static final String ITEM_REMOVE_FAILED_MESSAGE = "Cart item removal failed";

	public static final String CART_CLEARED_MESSAGE = "Cart cleared successfully";
	public static final String CART_CLEAR_FAIL_MESSAGE = "Cart clearation failed";

	// ==========================
	// ENTITY TYPES
	// ==========================

	public static final String ENTITY_TYPE_CART = "CART";

	public static final String ENTITY_TYPE_CART_ITEM = "CART_ITEM";

	public static final String ENTITY_ORDER = "ORDER";
	public static final String ENTITY_PAYMENT = "PAYMENT";
	public static final String ENTITY_CART = "CART";
	public static final String ENTITY_PRODUCT = "PRODUCT";
	public static final String ENTITY_USER = "USER";
	public static final String ENTITY_SHIPMENT = "SHIPMENT";

	// ===========================================================
	// ORDER STATUS
	// ===========================================================

	public static final String ORDER_STATUS_PENDING = "PENDING";

	public static final String ORDER_STATUS_PENDING_PAYMENT = "PENDING_PAYMENT";

	public static final String ORDER_STATUS_CONFIRMED = "CONFIRMED";

	public static final String ORDER_STATUS_PROCESSING = "PROCESSING";

	public static final String ORDER_STATUS_PACKED = "PACKED";

	public static final String ORDER_STATUS_SHIPPED = "SHIPPED";

	public static final String ORDER_STATUS_OUT_FOR_DELIVERY = "OUT_FOR_DELIVERY";

	public static final String ORDER_STATUS_DELIVERED = "DELIVERED";

	public static final String ORDER_STATUS_CANCELLED = "CANCELLED";

	public static final String ORDER_STATUS_RETURN_REQUESTED = "RETURN_REQUESTED";

	public static final String ORDER_STATUS_RETURNED = "RETURNED";

	public static final String ORDER_STATUS_REFUND_PENDING = "REFUND_PENDING";

	public static final String ORDER_STATUS_REFUNDED = "REFUNDED";

	// ===========================================================
	// ADDRESS CHANGE REASONS
	// ===========================================================

	public static final String ADDRESS_CREATED = "ADDRESS_CREATED";

	public static final String ADDRESS_UPDATED = "ADDRESS_UPDATED";

	// ===========================================================
	// CHANGED BY
	// ===========================================================

	public static final String CUSTOMER = "CUSTOMER";

	public static final String ADMIN = "ADMIN";

	public static final String SYSTEM = "SYSTEM";

	// ===========================================================
	// EVENTS
	// ===========================================================

	public static final String ORDER_CREATED_EVENT = "ORDER_CREATED";

	public static final String ORDER_FETCHED_EVENT = "ORDER_FETCHED";

	public static final String ORDERS_FETCHED_EVENT = "ORDERS_FETCHED";

	public static final String ORDER_UPDATED_EVENT = "ORDER_UPDATED";

	public static final String ORDER_CANCELLED_EVENT = "ORDER_CANCELLED";

	public static final String ORDER_CONFIRMED_EVENT = "ORDER_CONFIRMED";

	public static final String ORDER_STATUS_UPDATED_EVENT = "ORDER_STATUS_UPDATED";

	public static final String ORDER_ADDRESS_UPDATED_EVENT = "ORDER_ADDRESS_UPDATED";

	// ===========================================================
	// SUCCESS MESSAGES
	// ===========================================================

	public static final String ORDER_CREATED_MESSAGE = "Order placed successfully.";

	public static final String ORDER_FETCHED_MESSAGE = "Order fetched successfully.";

	public static final String ORDERS_FETCHED_MESSAGE = "Orders fetched successfully.";

	public static final String ORDER_UPDATED_MESSAGE = "Order updated successfully.";

	public static final String ORDER_CANCELLED_MESSAGE = "Order cancelled successfully.";

	public static final String ORDER_CONFIRMED_MESSAGE = "Order confirmed successfully.";

	public static final String ORDER_STATUS_UPDATED_MESSAGE = "Order status updated successfully.";

	public static final String ORDER_ADDRESS_UPDATED_MESSAGE = "Delivery address updated successfully.";

	// ===========================================================
	// FAILURE MESSAGES
	// ===========================================================

	public static final String ORDER_NOT_FOUND = "Order not found.";

	public static final String ORDER_ITEM_NOT_FOUND = "Order item not found.";

	public static final String ORDER_ALREADY_CANCELLED = "Order is already cancelled.";

	public static final String ORDER_ALREADY_DELIVERED = "Order has already been delivered.";

	public static final String ORDER_CANNOT_BE_CANCELLED = "Order cannot be cancelled.";

	public static final String ORDER_CANNOT_BE_UPDATED = "Order cannot be updated.";

	public static final String ORDER_ADDRESS_UPDATE_NOT_ALLOWED = "Delivery address cannot be updated.";

	public static final String ORDER_ALREADY_PAID = "Order has already been paid.";

	public static final String ORDER_CREATION_FAILED = "Failed to create order.";

	public static final String ORDER_UPDATE_FAILED = "Failed to update order.";

	public static final String ORDER_CANCEL_FAILED = "Failed to cancel order.";

	public static final String ORDER_FETCH_FAILED = "Failed to fetch order.";

	public static final String INVALID_ORDER_STATUS = "Invalid order status.";

	// ===========================================================
	// VALIDATION
	// ===========================================================

	public static final String INVALID_ORDER = "Invalid order.";

	public static final String EMPTY_ORDER = "Order cannot be empty.";

	public static final String EMPTY_ORDER_ITEMS = "Order items cannot be empty.";

	public static final String INVALID_ORDER_AMOUNT = "Invalid order amount.";

	public static final String INVALID_DELIVERY_ADDRESS = "Invalid delivery address.";

	public static final String ORDER_PAYMENT_PENDING = "Payment is pending.";

	public static final String ORDER_PAYMENT_FAILED = "Payment failed.";

	public static final String ORDER_PAYMENT_SUCCESS = "Payment completed successfully.";

	public static final String ORDER_WAITING_FOR_PAYMENT = "Waiting for payment confirmation.";

	public static final String ORDER_LOCKED = "Order is locked.";

	public static final String ORDER_MODIFICATION_NOT_ALLOWED = "Order modification is not allowed.";

	public static final String ORDER_ALREADY_SHIPPED = "Order has already been shipped.";
	public static final String CART_EMPTY_MESSAGE = "Cart is empty, Please add something first.";
	public static final String ORDER_ADDRESS_NOT_FOUND = "ORDER_ADDRESS_NOT_FOUND";
	public static final String ORDER_ALREADY_CANCELLED_MESSAGE = "Order has already been cancelled.";
	public static final String ORDER_SHIPPED = "ORDER_SHIPPED";
	public static final String ORDER_DELIVERED = "ORDER_DELIVERED";
	public static final String ORDER_CANNOT_BE_CANCELLED_MESSAGE = "Order can not be cancelled.";
	public static final String ORDER_PACKED = "ORDER_PACKED";
	public static final String INVALID_ORDER_STATUS_MESSAGE = "Order status is invalid.";
	public static final Object ORDER_CONFIRMED = "ORDER_CONFIRMED";
	public static final Object ORDER_PROCESSING = "ORDER_PROCESSING";
	public static final String PAYMENT_CREATED = "PAYMENT_CREATED";
	public static final String PAYMENT_CREATED_MESSAGE = "Payment is created.";
	public static final String PAYMENT_FETCHED = "PAYMENT_FETCHED";
	public static final String PAYMENT_FETCHED_MESSAGE = "Payment has been fetched";
	public static final String ORDER_FETCHED = "ORDER_FETCHED";
	public static final String ORDER_UPDATED = "ORDER_UPDATED";
	public static final String PAYMENT_PENDING = "PAYMENT_PENDING";
	public static final String ORDER_ALREADY_PAID_MESSAGE = "Order is already paid.";
	public static final String ORDER_NOT_ELIGIBLE_FOR_PAYMENT = "Order is invalid.";
	public static final String INR = "INR";
	public static final String RAZORPAY = "RAZORPAY";
	public static final String PAYMENT_NOT_FOUND = "Payment not found.";
	public static final String PAYMENT_PROVIDER_ERROR = "Internal error with payment provider!!";
	public static final String PAYMENT_VERIFIED = "PAYMENT_VERIFIED";
	public static final String PAYMENT_VERIFIED_MESSAGE = "Payment is verified. Please proceed.";
	public static final String PAYMENT_METHOD_ONLINE = "ONLINE";

	// =========================================================================
	// 🔥 INVENTORY SERVICE CONSTANTS (Response Codes & Messages)
	// =========================================================================

	// Response Codes
	public static final String CODE_STOCK_INITIALIZED = "STOCK_INITIALIZED";
	public static final String CODE_STOCK_RESERVED = "STOCK_RESERVED";
	public static final String CODE_STOCK_CONFIRMED = "STOCK_CONFIRMED";
	public static final String CODE_STOCK_RELEASED = "STOCK_RELEASED";
	public static final String CODE_STOCK_FETCHED = "STOCK_FETCHED";

	// Response Messages
	public static final String MSG_STOCK_INITIALIZED = "Stock initialized successfully";
	public static final String MSG_STOCK_RESERVED = "Stock reserved successfully";
	public static final String MSG_STOCK_CONFIRMED = "Stock reservation confirmed";
	public static final String MSG_STOCK_RELEASED = "Stock released successfully";
	public static final String MSG_STOCK_FETCHED = "Stock fetched successfully";
	public static final String MSG_STOCK_UPDATE_FAILED = "Stock updation failed";
	public static final String CODE_BULK_STOCK_FETCHED = "BULK_STOCK_FETCHED";
	public static final String MSG_BULK_STOCK_FETCHED = "Bulk stock details fetched successfully";
	public static final String EMAIL = "EMAIL";
	public static final String NORMAL = "NORMAL";
	public static final String ACTIVE = "ACTIVE";
	public static final String TO = "TO";
	public static final String CC = "CC";
	public static final String BCC = "BCC";
	public static final String UNAUTHORIZED = "UNAUTHORIZED";
	public static final String INVALID_REQUEST = "INVALID_REQUEST";
	public static final String HASH_ALGO = "SHA-256";
	public static final String CONFLICT = "CONFLICT";
	public static final String CORRELATION_ID_HEADER = "X-Maithil-Trace";
	public static final String MDC_KEY = "pulse";
	public static final String CALLER_SERVICE_HEADER = "X-Caller-Service";
	public static final String REQUEST_START_TIME = "X-Request-Start-Time";

	public final class CacheConstants {

		public static final int TTL_MINUTES = 30;
		public static final String PRODUCT = "product";
		public static final String CATEGORY = "category";
		public static final String USER = "user";
		public static final String ORDER = "order";
		public static final String PRODUCT_ID = "product:id:";
		public static final String PRODUCT_SLUG = "product:slug:";
		public static final String CATGEORY_SLUG = "category:slug:";
		public static final String CATEGORY_ID = "category:id:";
		public static final String MERCHANT_ID = "merchant:id:";
		public static final String IDEMPOTENCY = "idempotency:";

		private CacheConstants() {
		}
	}

	public enum IdempotencyStatus {

		COMPLETED, IN_PROGRESS
	}

	public final class MetricsConstants {
		public static final String APPLICATION = "application";
		public static final String SERVICE = "service";
		public static final String ENVIRONMENT = "environment";
		public static final String VERSION = "version";
	}

	public final class CacheMetricNames {

		public static final String HIT = "maithil.cache.hit";
		public static final String MISS = "maithil.cache.miss";
		public static final String PUT = "maithil.cache.put";
		public static final String DELETE = "maithil.cache.delete";
		public static final String ERROR = "maithil.cache.error";

		public static final String OPERATION = "maithil.cache.operation";
	}
	
	public final class CacheOperationNames {

	    public static final String GET = "GET";
	    public static final String PUT = "PUT";
	    public static final String DELETE = "DELETE";
	    public static final String EXISTS = "EXISTS";
	    public static final String EXPIRE = "EXPIRE";
	    public static final String CLEAR = "CLEAR";
	    public static final String INCREMENT = "INCREMENT";
	    public static final String SETNX = "SETNX";

	    private CacheOperationNames() {}
	}
}