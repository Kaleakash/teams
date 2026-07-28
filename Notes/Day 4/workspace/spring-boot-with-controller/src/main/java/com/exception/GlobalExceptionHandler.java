package com.exception;



import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Intercepts your custom Product business errors
    @ExceptionHandler(ProductException.class)
    public String handleProductException(ProductException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error-view"; // Redirects to error-view.html template
    }

    // 2. Fallback catcher to handle unexpected runtime system exceptions
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "An unexpected system error occurred: " + ex.getMessage());
        return "error-view";
    }
}

