package servidorblogapsrmi;

import br.com.utfpr.pojo.Usuario;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hugo
 */
public class Cliente {
    static int op;
    
    public static void main(String[] args) {        
        try {            
            Registry registro = LocateRegistry.getRegistry("localhost", 1099);
            
            FuncoesBlog canal = (FuncoesBlog) registro.lookup("blog");
            
            Scanner input = new Scanner(System.in);
            List<Usuario> usuarios;
            
            while(op != 4) {
                System.out.println("");                
                System.out.println("============== MENU ============== ");
                System.out.println("1 - Cadastrar Usuario");
                System.out.println("2 - Listar Usuarios");
                System.out.println("3 - Excluir Usuario");
                System.out.println("4 - Sair");
                System.out.println("Opção:");
                op = input.nextInt();

                switch(op) {
                    case 1:
                        Usuario usu = new Usuario();
                        
                        System.out.println("Nome:");
                        String nome = input.next();
                        input.nextLine();
                        System.out.println("Email:");
                        String email = input.next();
                        input.nextLine();
                        System.out.println("Senha:");
                        String senha = input.next();
                        input.nextLine();
                        usu.setNome(nome);
                        usu.setEmail(email);
                        usu.setSenha(senha);
                        
                        if(canal.cadastrarUsuario(usu)) {
                            System.out.println("Usuário cadastrado com sucesso!");
                        } else {
                            System.out.println("Erro ao cadastrar usuário!");
                        }
                        
                        break;
                    case 2: 
                        usuarios = canal.listarUsuarios();
                        
                        System.out.println("");
                        for(Usuario u : usuarios) {
                            System.out.println("ID: " + u.getId() + " Nome: " + u.getNome());
                        } 
                        break;
                    case 3:
                        System.out.println("ID do usuário que deseja excluir:");
                        int id = input.nextInt();
                        
                        usuarios = canal.listarUsuarios();
                        Usuario usuEx = null;
                                
                        for(Usuario u : usuarios) {
                            if(u.getId() == id) {
                                usuEx = u;
                            }
                        }
                        
                        if(usuEx != null) {
                            if(canal.excluirUsuario(usuEx)) {
                                System.out.println("Usuario excluido com sucesso!");
                            } else {
                                System.out.println("Erro ao excluir usuario!");
                            }
                        } else {
                            System.out.println("Usuario não encontrado!");
                        }
                        break;
                    default:
                        System.out.println("Opção invalida!");
                        break;
                }
            }            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ServidorBlog.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
