package wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wallet.dto.WalletOperationRequest;
import wallet.dto.WalletResponse;
import wallet.model.OperationType;
import wallet.model.Wallet;
import wallet.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public WalletResponse processOperation(WalletOperationRequest request) {
        // Блокируем кошелек для изменения
        Wallet wallet = walletRepository.findWithLockingById(request.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException(
                        "Wallet not found: " + request.getWalletId()));

        BigDecimal newBalance;

        // Обрабатываем операцию в зависимости от типа
        if (request.getOperationType() == OperationType.DEPOSIT) {
            newBalance = deposit(wallet, request.getAmount());
        } else {
            newBalance = withdraw(wallet, request.getAmount());
        }

        // Обновляем баланс
        wallet.setBalance(newBalance);
        Wallet savedWallet = walletRepository.save(wallet);

        return new WalletResponse(savedWallet.getId(), savedWallet.getBalance());
    }

    private BigDecimal deposit(Wallet wallet, BigDecimal amount) {
        return wallet.getBalance().add(amount);
    }

    private BigDecimal withdraw(Wallet wallet, BigDecimal amount) {
        BigDecimal currentBalance = wallet.getBalance();

        if (currentBalance.compareTo(amount) < 0) {
            throw new InsufficientFundsException(
                    "Insufficient funds. Current balance: " + currentBalance +
                            ", requested: " + amount);
        }

        return currentBalance.subtract(amount);
    }

    @Transactional(readOnly = true)
    public WalletResponse getBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(
                        "Wallet not found: " + walletId));

        return new WalletResponse(wallet.getId(), wallet.getBalance());
    }
}
