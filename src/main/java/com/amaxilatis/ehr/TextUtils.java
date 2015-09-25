package com.amaxilatis.ehr;

/**
 * Utility class with methods for working with {@link String}s.
 *
 * @author <href="mailto:dzarras@cti.gr">Dimitris Zarras</a>
 */
public final class TextUtils {
    private TextUtils() {
        throw new RuntimeException("unsupported operation");
    }

    /**
     * Checks if the given {@link String} is empty.
     *
     * @param string The {@link String} to check.
     * @return true if the given {@link String} is null or empty as per {@link String#isEmpty()},
     *         false otherwise.
     */
    public static boolean isEmpty(final String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Null-safe equality check between two {@link String}s.
     *
     * @param left The first {@link String}.
     * @param right The second {@link String}.
     * @return true if both left and right are null, false if any of left or right is null and the other isn't, the
     *         result of {@link String#equals(Object)} if both left and right are non-null.
     */
    public static boolean equals(final String left, final String right) {
        if (left == null && right == null) {
            //Both are null
            return true;

        } else if (left == null || right == null) {
            //The one is null and the other isn't
            return false;

        } else {
            //Both are non null, so let String.equals() decide
            return left.equals(right);
        }
    }
}
