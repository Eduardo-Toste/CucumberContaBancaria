import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author: Eduardo Ciochetti Toste
 * @version: 1.2
 * @since: Release 1.0 da aplicação
 * 
 * Classe que representa uma conta bancária.
 */
public class Conta {
    private String tipoCliente;
    private float saldo;
    private float valorSaque;

    /**
     * Construtor da classe Conta.
     *
     * @param tipoCliente Tipo do cliente (especial ou comum).
     * @param saldo Saldo inicial da conta.
     * @param valorSaque Valor do saque a ser solicitado.
     */
    public Conta(String tipoCliente, float saldo, float valorSaque) {
        this.tipoCliente = tipoCliente;
        this.saldo = saldo;
        this.valorSaque = valorSaque;
    }

    /**
     * Obtém o tipo do cliente.
     *
     * @return Tipo do cliente.
     */
    public String getTipoCliente() {
        return tipoCliente;
    }

    /**
     * Obtém o saldo da conta.
     *
     * @return Saldo atual da conta.
     */
    public float getSaldo() {
        return saldo;
    }

    /**
     * Obtém o valor do saque.
     *
     * @return Valor do saque.
     */
    public float getvalorSaque() {
        return valorSaque;
    }

    /**
     * Método para solicitar um saque.
     *
     * @param valor Valor do saque solicitado.
     */
    public void solicitarSaque(double valor) {
        if (tipoCliente.equals("especial")) {
            efetuarSaqueEspecial(valor);
        } else {
            efetuarSaqueComum(valor);
        }
    }

    /**
     * Método privado para efetuar um saque para clientes especiais.
     *
     * @param valor Valor do saque.
     * @throws RuntimeException Se o saldo for insuficiente.
     */
    private void efetuarSaqueEspecial(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
        } else {
            throw new RuntimeException("Saldo Insuficiente");
        }
    }

    /**
     * Método privado para efetuar um saque para clientes comuns.
     *
     * @param valor Valor do saque.
     * @throws RuntimeException Se o saldo for insuficiente.
     */
    private void efetuarSaqueComum(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
        } else {
            throw new RuntimeException("Saldo Insuficiente");
        }
    }

    private Conta conta;

    /**
     * Configuração inicial para um cliente especial com saldo.
     *
     * @param saldoInicial Saldo inicial do cliente especial.
     */
    @Given("^Um cliente especial com saldo de -(\\d+) reais$")
    public void um_cliente_especial_com_saldo_de_reais(int saldoInicial) {
        conta = new Conta("especial", saldoInicial, saldo);
    }

    /**
     * Processo de saque para um cliente especial.
     *
     * @param valorSaque Valor do saque solicitado.
     */
    @When("^for solicitado um saque de (\\d+) reais$")
    public void for_solicitado_um_saque_de_reais(int valorSaque) {
        conta.solicitarSaque(valorSaque);
    }

    /**
     * Verificação do saldo após o saque para um cliente especial.
     *
     * @param novoSaldo Novo saldo esperado após o saque.
     */
    @Then("^deve efetuar o saque e atualizar o saldo para -(\\d+) reais$")
    public void deve_efetuar_o_saque_e_atualizar_o_saldo_para_reais(int novoSaldo) {
        conta.solicitarSaque(valorSaque);
        if (conta.getSaldo() == -valorSaque) {
            System.out.println("Saque efetuado com sucesso. Novo saldo: " + conta.getSaldo());
        } else {
            throw new RuntimeException("Erro ao efetuar saque. Saldo atual: " + conta.getSaldo());
        }
    }

    /**
     * Verificação adicional.
     */
    @Then("^check more outcomes$")
    public void check_more_outcomes() {
    }

    /**
     * Configuração inicial para um cliente comum com saldo.
     *
     * @param saldoInicial Saldo inicial do cliente comum.
     */
    @Given("^Um cliente comum com saldo de -(\\d+) reais$")
    public void um_cliente_comum_com_saldo_de_reais(int saldoInicial) {
        conta = new Conta("comum", saldoInicial, saldo);
    }

    /**
     * Processo de solicitação de saque para um cliente comum.
     *
     * @param valorSaque Valor do saque solicitado.
     */
    @When("^solicitar um saque de (\\d+) reais$")
    public void solicitar_um_saque_de_reais(int valorSaque) {
        conta.solicitarSaque(valorSaque);
        System.out.println(saldo);
    }

    /**
     * Verificação de não efetuar o saque e retornar mensagem de saldo insuficiente para um cliente comum.
     */
    @Then("^não deve efetuar o saque e deve retornar a mensagem Saldo Insuficiente$")
    public void não_deve_efetuar_o_saque_e_deve_retornar_a_mensagem_Saldo_Insuficiente() {
        throw new RuntimeException("Saldo Insuficiente");
    }
}