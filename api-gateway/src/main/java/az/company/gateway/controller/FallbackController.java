package az.company.gateway.controller;

import az.company.gateway.model.response.FallbackResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/productServiceFallback")
    public FallbackResponse productServiceFallback() {
        return new FallbackResponse("Product service is down!");
    }

    @GetMapping("/paymentServiceFallback")
    public FallbackResponse paymentServiceFallback() {
        return new FallbackResponse("Payment service is down!");
    }

    @GetMapping("/orderServiceFallback")
    public FallbackResponse orderServiceFallback() {
        return new FallbackResponse("Order service is down!");
    }
}
