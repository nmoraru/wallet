package wallet.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletResponse {
    private final UUID walletId;
    private final BigDecimal balance;

    public WalletResponse(UUID walletId, BigDecimal balance) {
        this.walletId = walletId;
        this.balance = balance;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "WalletResponse{" +
                "walletId=" + walletId +
                ", balance=" + balance +
                '}';
    }
}
