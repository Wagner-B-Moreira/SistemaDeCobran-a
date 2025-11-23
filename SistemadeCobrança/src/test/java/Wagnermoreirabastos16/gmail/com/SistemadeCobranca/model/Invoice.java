package Wagnermoreirabastos16.gmail.com.SistemadeCobranca.model;


import com.sun.istack.NotNull;
import jakarta.persistence.*;


import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Invoices")
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  // numero/identificador legivel de fatura
    @Column(nullable = false, unique = true)

    private String invoiceNumber;

    // valor total da fatura
    @NotNull
    private BigDecimal amount;

    // data de vencimento
    private LocalDate dueDate;

    // flag simples para indicar pagamento (poderia ser um status enumerado
    private boolean paid = false;

    // relacionamento many-to-One com customer (muitas faturas para um cliente)
   //  @ManyToMany
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private customer  customer;


    public  Invoice() { }
    public  Invoice(String invoiceNumber, BigDecimal amount, LocalDate dueDate, customer customer) {

        this.invoiceNumber = invoiceNumber;
        this.amount = amount;
        this.dueDate = dueDate;
        this.customer = customer;
        this.paid = false;

    }

    // getter e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public customer getCustomer() {
        return customer;
    }

    public void setCustomer(customer customer) {
        this.customer = customer;
    }
}
