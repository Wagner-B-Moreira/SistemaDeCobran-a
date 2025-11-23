
package Wagnermoreirabastos16.gmail.com.SistemadeCobranca.controller;

import Wagnermoreirabastos16.gmail.com.SistemadeCobranca.model.Invoice;
import Wagnermoreirabastos16.gmail.com.SistemadeCobranca.model.Payment;
import Wagnermoreirabastos16.gmail.com.SistemadeCobranca.service.InvoiceService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.time.LocalDate; // <-- Faltava importar LocalDate

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // criar fatura esperando um JSON
    @PostMapping // <-- RequestMapping sem método vira GET, corrigido para POST
    public ResponseEntity<?> createinvoice(@RequestBody Map<String , Object> payloand){

        try {
            // pega o ID do cliente vindo do JSON
            Long customerId = Long.valueOf(payloand.get("customerId").toString());

            // pega número da fatura
            String invoicenumber = payloand.get("invoiceNumber").toString();

            // pega valor
            BigDecimal amount = new BigDecimal(payloand.get("amount").toString());

            // pega data de vencimento
            LocalDate dueDate = LocalDate.parse(payloand.get("dueDate").toString());
            // ^ localDate estava com letra minúscula

            // cria fatura
            Invoice created = invoiceService.createInvoice(customerId, invoicenumber, amount, dueDate);

            // erro: ResponseEntity.ok()(created); → corrigido
            return ResponseEntity.ok(created);

        } catch (Exception e) {

            // retorna 400 com mensagem de erro
            return ResponseEntity.badRequest().body(Map.of("Error" , e.getMessage()));
        }
    } // <-- FECHAMENTO correto DO MÉTODO

    // listar faturas
    @GetMapping
    public ResponseEntity<List<Invoice>> listInvoices() {
        return ResponseEntity.ok(invoiceService.listAll());
    }

    // buscar fatura por id
    @GetMapping("{id}")
    public ResponseEntity<?> getInvoice(@PathVariable Long id) {
        try {

            // retorna fatura encontrada
            return ResponseEntity.ok(invoiceService.getInvoice(id));

        } catch (Exception e) {

            // retorna 404 caso não exista
            return ResponseEntity.status(404).body(Map.of("Error", e.getMessage()));
        }
    }

    // pagar fatura: POST
    @PostMapping("{id}/pay")
    public ResponseEntity<?> payInvoice(@PathVariable Long id, @RequestBody Map<String , Object> payloand ) {
        //                 ^ faltava vírgula entre id e @RequestBody

        try {
            // estava escrito payloan errado → corrigido
            BigDecimal amount = new BigDecimal(payloand.get("amount").toString());

            // erro de sintaxe → faltava parêntese
            Payment payment = invoiceService.payInvoice(id, amount);

            return ResponseEntity.ok(payment);

        } catch (Exception e ) {

            return ResponseEntity.badRequest().body(Map.of("Error" , e.getMessage()));
        }
    }

    // deletar fatura (apenas se não paga)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Long id) {

        try {

            invoiceService.deleteInvoice(id);

            return ResponseEntity.noContent().build();

        } catch (Exception e ) {

            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
