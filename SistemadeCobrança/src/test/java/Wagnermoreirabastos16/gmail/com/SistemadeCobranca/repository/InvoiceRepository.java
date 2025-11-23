package Wagnermoreirabastos16.gmail.com.SistemadeCobranca.repository;


import Wagnermoreirabastos16.gmail.com.SistemadeCobranca.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository  extends JpaRepository<Invoice, Long> {

    // Buscar por numeros (util para evitar duplicidade
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
}
