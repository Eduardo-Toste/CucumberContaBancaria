import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Conta {
    private String tipoCliente;
    private float saldo;
    private float valorSaque;

    public Conta(String tipoCliente, float saldo, float valorSaque) {
        this.tipoCliente = tipoCliente;
        this.saldo = saldo;
        this.valorSaque = valorSaque;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public float getSaldo() {
        return saldo;
    }
    
    public float getvalorSaque() {
        return valorSaque;
    }

    public void solicitarSaque(double valor) {
        if (tipoCliente.equals("especial")) {
            efetuarSaqueEspecial(valor);
        } else {
            efetuarSaqueComum(valor);
        }
    }

    private void efetuarSaqueEspecial(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
        } else {
        	throw new RuntimeException("Saldo Insuficiente");
        }
    }

    private void efetuarSaqueComum(double valor) {
    	if (valor <= saldo) {
            saldo -= valor;
        } else {
        	throw new RuntimeException("Saldo Insuficiente");
        }
    }
	
	private Conta conta;
	
	@Given("^Um cliente especial com saldo de -(\\d+) reais$")
    public void um_cliente_especial_com_saldo_de_reais(int saldoInicial) {
        conta = new Conta("especial", saldoInicial, saldo);
    }

    @When("^for solicitado um saque de (\\d+) reais$")
    public void for_solicitado_um_saque_de_reais(int valorSaque) {
        conta.solicitarSaque(valorSaque);
    }

    @Then("^deve efetuar o saque e atualizar o saldo para -(\\d+) reais$")
    public void deve_efetuar_o_saque_e_atualizar_o_saldo_para_reais(int novoSaldo) {
    	conta.solicitarSaque(valorSaque);
        if (conta.getSaldo() == -valorSaque) {
            System.out.println("Saque efetuado com sucesso. Novo saldo: " + conta.getSaldo());
        } else {
            throw new RuntimeException("Erro ao efetuar saque. Saldo atual: " + conta.getSaldo());
        }
    }

    @Then("^check more outcomes$")
    public void check_more_outcomes() {
    }

    @Given("^Um cliente comum com saldo de -(\\d+) reais$")
    public void um_cliente_comum_com_saldo_de_reais(int saldoInicial) {
        conta = new Conta("comum", saldoInicial, saldo);
    }

    @When("^solicitar um saque de (\\d+) reais$")
    public void solicitar_um_saque_de_reais(int valorSaque) {
        conta.solicitarSaque(valorSaque);
        System.out.println(saldo);
    }

    @Then("^não deve efetuar o saque e deve retornar a mensagem Saldo Insuficiente$")
    public void não_deve_efetuar_o_saque_e_deve_retornar_a_mensagem_Saldo_Insuficiente() {
    	throw new RuntimeException("Saldo Insuficiente");
    }

}