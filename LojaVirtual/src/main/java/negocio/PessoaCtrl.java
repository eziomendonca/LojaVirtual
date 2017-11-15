package negocio;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import beans.Fone;
import beans.Pessoa;
import beans.Produto;
import persistencia.PessoaDAO;
import persistencia.ProdutoDAO;

@ManagedBean
@SessionScoped
public class PessoaCtrl implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pessoa pessoa = new Pessoa();
	private String filtro = "";

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Pessoa> getListagem() {
		return PessoaDAO.listagem(filtro);
	}

	public String actionGravar() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (pessoa.getId() == 0) {
			PessoaDAO.inserir(pessoa);
			context.addMessage(null, new FacesMessage("Sucesso", "Inserido com sucesso!"));

		} else {
			PessoaDAO.alterar(pessoa);
			context.addMessage(null, new FacesMessage("Sucesso", "Alterado com sucesso!"));

		}
		return "/admin/lista_pessoa";
	}

	public String actionInserir() {
		pessoa = new Pessoa();
		return "/admin/lista_pessoa";
	}

	public String actionExcluir() {
		PessoaDAO.excluir(pessoa);
		return "/admin/lista_pessoa";

	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Pessoa Selecionada", 
				String.valueOf(((Pessoa) event.getObject()).getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public String actionInserirFone() {
		Fone fone = new Fone();
		fone.setPessoa(pessoa);
		pessoa.getFones().add(fone);
		return "/admin/lista_pessoa";
	}

	public String actionExcluirFone(Fone f) {
		pessoa.getFones().remove(f);
		return "/admin/lista_pessoa";
	}

	
}
