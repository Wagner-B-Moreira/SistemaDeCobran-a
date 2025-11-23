package Wagnermoreirabastos16.gmail.com.SistemadeCobranca.controller;

import Wagnermoreirabastos16.gmail.com.SistemadeCobranca.model.customer;
import Wagnermoreirabastos16.gmail.com.SistemadeCobranca.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Remove import errado (TargetEmbeddable) — não é necessário

@RestController
@RequestMapping("/api/custumers") // Mantido exatamente como você escreveu
public class CustomerController {

    private final CustomerRepository customerRepository;

    // Construtor recebendo o repositório
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Criar um cliente (POST /api/custumers)
    @PostMapping
    public ResponseEntity<customer> createCustomer(@RequestBody customer customer) {

        // Salva o cliente no banco
        customer saved = customerRepository.save(customer);

        // Retorna o cliente salvo
        return ResponseEntity.ok(saved);
    }

    // Listar todos os clientes (GET /api/custumers)
    @GetMapping
    public ResponseEntity<List<customer>> listCustomer() {  // Correção: Customers → Customer

        // Retorna a lista completa de clientes
        return ResponseEntity.ok(customerRepository.findAll());
    }

    // Buscar cliente por ID (GET /api/custumers/{id})
    @GetMapping("/{id}") // Correção: precisava da barra /
    public ResponseEntity<customer> getCustomer(@PathVariable Long id) { // Correção: Customers → Customer

        // Correção: findAllById NÃO retorna Optional → trocado para findById
        return customerRepository.findById(id)

                .map(ResponseEntity::ok)  // Se existir, retorna 200 OK

                .orElse(ResponseEntity.notFound().build()); // Se não existir, retorna 404
    }
}
