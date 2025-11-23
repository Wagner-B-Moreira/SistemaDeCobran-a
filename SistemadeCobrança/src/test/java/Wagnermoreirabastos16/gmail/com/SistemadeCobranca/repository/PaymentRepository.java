package Wagnermoreirabastos16.gmail.com.SistemadeCobranca.repository;

import Wagnermoreirabastos16.gmail.com.SistemadeCobranca.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
