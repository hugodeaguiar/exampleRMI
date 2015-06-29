package servidorblogapsrmi;

import br.com.utfpr.dao.ComentarioDao;
import br.com.utfpr.dao.PostDao;
import br.com.utfpr.dao.UsuarioDao;
import br.com.utfpr.pojo.Comentario;
import br.com.utfpr.pojo.Post;
import br.com.utfpr.pojo.Usuario;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author hugo
 */
public class ServidorBlog implements FuncoesBlog {
    private final EntityManagerFactory factory;
    private final EntityManager em;
    
    public ServidorBlog(){
        super();
        this.factory = Persistence.createEntityManagerFactory("ServidorBlogApsRmiPU");
        this.em = this.factory.createEntityManager();
    }
    
    @Override
    public boolean cadastrarUsuario(Usuario usuario) throws RemoteException {
       System.out.println("Cadastrar usuario");
       UsuarioDao dao = new UsuarioDao(this.em, this.factory);
               
       return dao.cadastrarUsuario(usuario);
    }

    @Override
    public boolean cadastrarPost(Post post) throws RemoteException{
        System.out.println("Cadastrar Post");
        PostDao dao = new PostDao(this.em, this.factory);
        
        return dao.cadastrarPost(post);
    }

    @Override
    public boolean cadastrarComentario(Comentario comentario) throws RemoteException{
        System.out.println("Cadastrar Comentario");
        ComentarioDao dao = new ComentarioDao(this.em, this.factory);
        
        return dao.cadastrarComentario(comentario);
    }

    @Override
    public boolean atualizarUsuario(Usuario usuario) throws RemoteException {
        System.out.println("Atualizar Usuário");
        UsuarioDao dao = new UsuarioDao(this.em, this.factory);
        
        return dao.atualizarUsuario(usuario);
    }

    @Override
    public boolean atualizarPost(Post post) throws RemoteException{
        System.out.println("Atualizar Post");
        PostDao dao = new PostDao(this.em, this.factory);
        
        return dao.atualizarPost(post);
    }
    
    @Override
    public List<Usuario> listarUsuarios() throws RemoteException {
        System.out.println("Listar Usuario");
        
        UsuarioDao dao = new UsuarioDao(this.em, this.factory);
        
        return dao.listarUsuario();
    }

    @Override
    public List<Comentario> listarComentarios(int idPost) throws RemoteException {
        System.out.println("Listar Comentarios");
        ComentarioDao dao = new ComentarioDao(this.em, this.factory);
        
        return dao.listarComentario(idPost); 
    }

    @Override
    public List<Post> listarPosts() throws RemoteException {
        System.out.println("Listar Posts");
        PostDao dao = new PostDao(this.em, this.factory);
        
        return dao.listarPost();
    }
    
    @Override
    public List<Post> listarMeusPosts(int id) throws RemoteException {
        System.out.println("Listar Meus Posts");
        PostDao dao = new PostDao(this.em, this.factory);
        
        return dao.listarMeusPosts(id);    
    }
    
    @Override
    public Usuario login(String email) throws RemoteException {
        System.out.println("Login");
        UsuarioDao dao = new UsuarioDao(this.em, this.factory);
        
        return dao.buscarPorEmail(email);   
    }
    
    @Override
    public Post buscarPostPorId(int idPost) throws RemoteException {
        System.out.println("Buscar Post Por ID");
        PostDao dao = new PostDao(this.em, this.factory);
        
        return dao.buscarPorId(idPost);
    }
    
    @Override
    public List<Usuario> listarUsuarioNome(String filtro) throws RemoteException {
        System.out.println("Listar Usuarios Por Nome");
        UsuarioDao dao = new UsuarioDao(this.em, this.factory);
        
        return dao.listarPorNome(filtro);
    }

    @Override
    public Usuario buscarUsuarioPorNome(String nome) throws RemoteException {
        System.out.println("Buscar Usuario Por Nome");
        UsuarioDao dao = new UsuarioDao(this.em, this.factory);
        
        return dao.buscarPorNome(nome);
    }
    
    @Override
    public boolean excluirUsuario(Usuario usuario) throws RemoteException {
        System.out.println("Excluir usuario");
        UsuarioDao usuDao = new UsuarioDao(this.em, this.factory);
        PostDao postDao = new PostDao(this.em, this.factory);
        ComentarioDao comentarioDao = new ComentarioDao(this.em, this.factory);
        
        if(comentarioDao.excluirComentarioPorUsuario(usuario.getId())) {
            if(postDao.excluirPostPorUsuario(usuario.getId())) {
                if(usuDao.excluirUsuario(usuario)) {
                    return true;
                }
            }
        }
        
        return false;
    }
     
    @Override
    public List<Post> buscarPostPorUsuario(int id) throws RemoteException {
        System.out.println("Buscar Post por usuario");
        PostDao dao = new PostDao(this.em, this.factory);
        
        return dao.listarPostsPorUsuario(id);
    }
        
    @Override
    public boolean excluirComentario(Comentario comentario) throws RemoteException {
        System.out.println("Excluir comentario");
        ComentarioDao dao = new ComentarioDao(this.em, this.factory);
        
        return dao.excluirComentario(comentario);
    }
    
    public static void main(String[] args) {
        ServidorBlog s = new ServidorBlog();
        
        /*
            Esta propriedade especifica os locais a partir dos quais as classes que são publicados por este VM
        */
        System.setProperty("java.rmi.codebase", "file:/home/hugo/NetBeansProjects/ServidorBlogRMI/build/classes/servidorblogapsrmi/");

        /* 
            Define o caminho do arquivo .policy no qual serão definidas as permissões 
        */
        System.setProperty("java.security.policy", "file:/home/hugo/NetBeansProjects/ServidorBlogRMI/src/servidorblogapsrmi/server.policy");
        
        /* 
           O valor desta propriedade representa a seqüência de nome de host, o valor default é o IP, 
           porem, no Notebook que foi utilizado tinha uma configuração no arquivo hosts do computador 
           que alterava o hostname, por isso teve que ser usado 
        */
        System.setProperty("java.rmi.server.hostname", "192.168.137.171");
               
        /* 
            SecurityManager para conceder ou negar acesso a recursos. Que sao definidos a partir do arquivo.policy 
        */
        if ( System.getSecurityManager() == null )
           System.setSecurityManager(new SecurityManager());
            
        try {
            FuncoesBlog canal = (FuncoesBlog) UnicastRemoteObject.exportObject(s,0);
            
            Registry registro = LocateRegistry.createRegistry(1099);
            
            registro.bind("blog", canal);
            
            System.out.println("Servidor pronto!");
            
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(ServidorBlog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
