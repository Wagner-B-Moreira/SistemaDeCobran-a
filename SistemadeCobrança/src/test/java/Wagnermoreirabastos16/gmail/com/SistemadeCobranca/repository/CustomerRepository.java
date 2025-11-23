package Wagnermoreirabastos16.gmail.com.SistemadeCobranca.repository;


import Wagnermoreirabastos16.gmail.com.SistemadeCobranca.model.customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // marca o componente de persistencia
public interface CustomerRepository extends JpaRepository<customer, Long> {

    // podemos adicionar metados customizados aqui ex: findByEmail (String email
}
