package in.maithilart.common.notification;

import java.util.Map;

import in.maithilart.common.constants.MaithilConstants;

public final class NotificationUtil {

    private NotificationUtil() {
    }

    public static Map<String, String> successToast(String title) {
        return Map.of(
                MaithilConstants.TYPE, MaithilConstants.SUCCESS,
                MaithilConstants.ACTION, MaithilConstants.SHOW_TOAST,
                MaithilConstants.TITLE, title
        );
    }

    public static Map<String, String> errorToast(String title) {
        return Map.of(
                MaithilConstants.TYPE, MaithilConstants.ERROR,
                MaithilConstants.ACTION, MaithilConstants.SHOW_TOAST,
                MaithilConstants.TITLE, title
        );
    }

    public static Map<String, String> warningToast(String title) {
        return Map.of(
                MaithilConstants.TYPE, MaithilConstants.WARNING,
                MaithilConstants.ACTION, MaithilConstants.SHOW_TOAST,
                MaithilConstants.TITLE, title
        );
    }
}