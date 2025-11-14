package wallet.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wallet.dto.ApiResponse;
import wallet.dto.WalletOperationRequest;
import wallet.dto.WalletResponse;
import wallet.service.WalletService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/wallet")
    public ResponseEntity<ApiResponse<WalletResponse>> processOperation(
            @Valid @RequestBody WalletOperationRequest request) {

        WalletResponse response = walletService.processOperation(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<ApiResponse<WalletResponse>> getBalance(
            @PathVariable UUID walletId) {

        WalletResponse response = walletService.getBalance(walletId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
