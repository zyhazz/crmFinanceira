package br.com.idealitajuba.crm.mbeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.idealitajuba.crm.business.BusinessException;
import br.com.idealitajuba.crm.business.CadastroCliente;
import br.com.idealitajuba.crm.model.Cliente;
import br.com.idealitajuba.crm.model.FontePagadoraEnum;
import br.com.idealitajuba.crm.model.SexoEnum;
import br.com.idealitajuba.crm.model.TipoBeneficio;
import br.com.idealitajuba.crm.repository.ClienteRepos;
import br.com.idealitajuba.crm.repository.TipoBeneficioRepos;
import br.com.idealitajuba.crm.util.Estado;

@Named
@ViewScoped
public class CadastroClienteMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroCliente cc;

	@Inject
	private TipoBeneficioRepos tbr;

	@Inject
	private ClienteRepos cr;

	private Cliente c;

	private SexoEnum[] sexo;
	private FontePagadoraEnum[] fonte;
	private List<TipoBeneficio> tipos;
	private Date hoje;
	private List<String> estados;
	
	public void preCadastro() {
		if (this.c == null) {
			this.c = new Cliente();
		}
		this.setTipos(tbr.todos());
		this.estados = Estado.ESTADOS;
	}
	
	public void limpar() {
		this.c = new Cliente();
	}
		
	public void salvar() throws BusinessException {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		String aviso = "";	
		try {
			this.c = this.cc.salvar(this.c);			
			context.addMessage(null, new FacesMessage("Salvo com sucesso!"));
		} catch (Exception e) {
			if (cr.porCpf(c.getCpf()) != null)
				aviso = "Erro ao realizar cadastro, CPF " + "já cadastrado para o (a) cliente "
							+ cr.porCpf(c.getCpf()).getNome() + ".";
			else
				aviso = e.getMessage();
			msg = new FacesMessage(aviso);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		}

	}

	/**
	 * Método usado para autocompletar a cidade digitada pelo usuário.
	 * 
	 * @param cidade
	 * @return
	 */
	public List<String> autoCompletaCidade(String cidade) {
		return this.cr.cidades(cidade);
	}

	
	/**
	 * Método usado para autocompletar o banco digitada pelo usuário.
	 * 
	 * @param banco
	 * @return
	 */
	public List<String> autoCompletaBanco(String banco) {
		return this.cr.bancos(banco);
	}
	
	public Cliente getC() {
		return c;
	}

	public void setC(Cliente c) {
		this.c = c;
	}

	public SexoEnum[] getSexo() {
		return SexoEnum.values();
	}

	public FontePagadoraEnum[] getFonte() {
		return FontePagadoraEnum.values();
	}

	public List<TipoBeneficio> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoBeneficio> tipos) {
		this.tipos = tipos;
	}

	public Date getHoje() {
		return new Date();
	}

	public void setHoje(Date hoje) {
		this.hoje = hoje;
	}

	public List<String> getEstados() {
		return estados;
	}

}
