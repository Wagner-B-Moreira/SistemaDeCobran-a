package Wagnermoreirabastos16.gmail.com.SistemadeCobranca.model;
import jakarta.persistence.*;

@Entity // mapeia a  classe para uma tabela no banco
@Table(name = "custumers")
public class customer {

    @Id // chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremeto
    private Long id;
    private  String name;
    private  String email;


    // Constructor padra do jpa
    public customer() { }


    // Constructor para facilitar criaçao
    public customer(String name, String email) {

        this.name = name;
        this.email = email;

    }
    // getters e setters  (encapsulamento)
    public Long getId() {return  id;}
    public void setId (Long Id) {this.id = id; }


    public String getNome() {return name; }
    public void setName(String  name ) {this.name = name; }

    public String getEmail() {return name;}
    public void setEmail(String email) {this.email = email;}

};
