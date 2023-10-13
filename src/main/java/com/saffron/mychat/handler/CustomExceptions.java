package com.saffron.mychat.handler;

/**
 * Contains custom exceptions used within the application.
 */
public class CustomExceptions {

    /**
     * Exception thrown when a resource is not found.
     */
    public static class NotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public NotFoundException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown for bad request errors.
     */
    public static class BadRequestException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public BadRequestException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown for duplicate entry errors.
     */
    public static class DuplicateEntryException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public DuplicateEntryException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown for unknown errors.
     */
    public static class UnknownException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public UnknownException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown when a user is not found.
     */
    public static class UserNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public UserNotFoundException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown for unauthorized access.
     */
    public static class UnauthorizedException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public UnauthorizedException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown when a business is not found.
     */
    public static class BusinessNotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public BusinessNotFoundException(String message) {
            super(message);
        }
    }
}
