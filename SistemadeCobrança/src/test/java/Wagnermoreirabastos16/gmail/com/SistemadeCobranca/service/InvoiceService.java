// Pacote correto (antes estava escrito "ackage")
package Wagnermoreirabastos16.gmail.com.SistemadeCobranca.service;

// Imports necessários
import Wagnermoreirabastos16.gmail.com.SistemadeCobranca.model.Invoice;
import Wagnermoreirabastos16.gmail.com.SistemadeCobranca.model.Payment;
import Wagnermoreirabastos16.gmail.com.SistemadeCobranca.model.customer;
import Wagnermoreirabastos16.gmail.com.SistemadeCobranca.repository.CustomerRepository;
import Wagnermoreirabastos16.gmail.com.SistemadeCobranca.repository.InvoiceRepository;
import Wagnermoreirabastos16.gmail.com.SistemadeCobranca.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Marca a classe como um serviço de negócio do Spring
@Service
public class InvoiceService {

    // Dependências para acessar os repositórios
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final PaymentRepository paymentRepository;

    // Construtor que recebe os repositórios (Injeção de Dependência)
    public InvoiceService(InvoiceRepository invoiceRepository,
                          CustomerRepository CustomerRepository, // Nome pode ser confuso, mas mantido conforme seu código
                          PaymentRepository paymentRepository) {

        // Inicializa os campos da classe com os repositórios recebidos
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = CustomerRepository;
        this.paymentRepository = paymentRepository;
    }

    // Cria uma fatura nova
    @Transactional
    public Invoice createInvoice(Long customerId, String invoiceNumber, BigDecimal amount, LocalDate dueDate) {

        // Busca o cliente — antes estava escrito "Costumer", errado.
        customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente nao encontrado:" + customerId));

        // Evita duplicidade de número de fatura (exixt → exist)
        Optional<Invoice> exist = invoiceRepository.findByInvoiceNumber(invoiceNumber);

        if (exist.isPresent()) {
            throw new IllegalArgumentException("numero de fatura ja existe" + invoiceNumber);
        }

        // Cria o objeto invoice (somente em memória)
        Invoice invoice = new Invoice(invoiceNumber, amount, dueDate, customer);

        // Persiste no banco e retorna
        return invoiceRepository.save(invoice);
    }

    // Busca uma fatura pelo ID
    public Invoice getInvoice(Long id) {

        // Correção: findAllById NÃO retorna Optional → trocado por findById
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("fatura nao encotrada" + id));
    }

    // Lista todas as faturas
    public List<Invoice> listAll() {
        return invoiceRepository.findAll();
    }

    // Marca uma fatura como paga e registra o pagamento
    @Transactional
    public Payment payInvoice(Long invoiceId, BigDecimal amount) {

        // Buscar fatura — findAllById → CORRIGIDO para findById
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("fatura nao encontrada:" + invoiceId));

        // Verifica se já está paga
        if (invoice.isPaid()) {
            throw new IllegalArgumentException("fatura ja esat paga:" + invoiceId);
        }

        // Garante que o valor pago é suficiente
        if (amount.compareTo(invoice.getAmount()) < 0) {
            throw new IllegalArgumentException("valor pago interios ao total da farura");
        }

        // Criar o pagamento (no Java, ainda não está salvo)
        Payment payment = new Payment(amount, LocalDateTime.now(), invoice);

        // Salvar no banco
        Payment saved = paymentRepository.save(payment);

        // Atualizar status da fatura
        invoice.setPaid(true);
        invoiceRepository.save(invoice);

        return saved;
    }

    // Remove fatura (desde que NÃO esteja paga)
    @Transactional
    public void deleteInvoice(Long invoiceId) {

        // Busca a fatura — findAllById → CORRIGIDO para findById
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("fatura nao encontrada:" + invoiceId));

        // Impede remover se estiver paga
        if (invoice.isPaid()) {
            throw new IllegalArgumentException("Nao é possivel remover fatuta paga");
        }

        // Deleta a fatura
        invoiceRepository.delete(invoice);
    }
}
