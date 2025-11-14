package wallet.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wallet.dto.ApiResponse;
import wallet.service.WalletService;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WalletService.WalletNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleWalletNotFound(WalletService.WalletNotFoundException e) {
        return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(WalletService.InsufficientFundsException.class)
    public ResponseEntity<ApiResponse<?>> handleInsufficientFunds(WalletService.InsufficientFundsException e) {
        return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(Exception e) {
        return ResponseEntity.badRequest().body(ApiResponse.error("Invalid request data"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleJsonParseException(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(ApiResponse.error("Invalid JSON format"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception e) {
        return ResponseEntity.internalServerError().body(ApiResponse.error("Internal server error"));
    }
}
