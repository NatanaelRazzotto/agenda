package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main","/insert","/select","/update"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		// test de conexão
		// dao.testeConexao();
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		}
		else if (action.equals("/insert")) {
			novoContato(request,response);
		}
		else if (action.equals("/select")) {
			listarContato(request,response);
		}
		else if (action.equals("/update")) {
			editarContato(request,response);
		}
		else {
			response.sendRedirect("index.html");
		}

	}

	// Listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//response.sendRedirect("agenda.jsp");
		
		//criando um objeto que ira receber um objeto javabeans
		ArrayList<JavaBeans> lista = dao.listarContatos();
		/*for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i).getIdcon());
			System.out.println(lista.get(i).getNome());
			System.out.println(lista.get(i).getFone());
			System.out.println(lista.get(i).getEmail());
		}*/
		
		//encaminha a lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
		
	}
	//Inserir
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			//teste de recebimento dos contatos
		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("fone"));
		System.out.println(request.getParameter("email"));
		
		//setar as variaveis JavaBeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));  
		contato.setEmail(request.getParameter("email"));
		
		//invocar o metodo inserir passando o objeto contato
		dao.inserirContato(contato);
		//redirecionar para o documento  agenda.jsp
		response.sendRedirect("main");
		
	}
	//editar contato	
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		//recebimento do id do contato que será editado
		String idcon = request.getParameter("idcon");
		//setar a variavel javabeans
		contato.setIdcon(idcon);
		//execultar o metodo selecionarcontato(DAO)
		dao.selecionarContato(contato);
		//teste de recebimento
		System.out.println(contato.getIdcon());
		System.out.println(contato.getNome());
		//setar os atributos do formulario com o conteudo do java beans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		//encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}
	
	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		//teste de recebimento
		System.out.println(request.getParameter("idcon"));
		//setar as variaveis javabeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));  
		contato.setEmail(request.getParameter("email"));
		//executar o method
		dao.alterarContato(contato);
		//redirecionar atualizado
		response.sendRedirect("main");
	}


}
