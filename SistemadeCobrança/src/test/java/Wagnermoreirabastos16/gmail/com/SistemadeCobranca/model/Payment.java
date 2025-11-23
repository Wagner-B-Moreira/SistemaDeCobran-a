package Wagnermoreirabastos16.gmail.com.SistemadeCobranca.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    // quanto foi Pago
    private BigDecimal amount;

    // memento do pagamento
    private LocalDateTime paymentDate;

    // relacionamento many-to-One com invoice (muitos pagamento podem apontar a uma fatura,
    // aqui simplificamos para um pagamento = marca fatura como paga )
    // @ManyToMany

    @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "invoice_id")

    private Invoice invoice;

     public Payment() { }

    public Payment(BigDecimal amount, LocalDateTime paymentDate, Invoice invoice) {

         this.amount = amount;
         this.paymentDate = paymentDate;
         this.invoice = invoice;
    }


    // getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
