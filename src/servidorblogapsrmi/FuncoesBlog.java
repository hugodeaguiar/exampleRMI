/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorblogapsrmi;

import br.com.utfpr.pojo.Comentario;
import br.com.utfpr.pojo.Post;
import br.com.utfpr.pojo.Usuario;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author hugo
 */
public interface FuncoesBlog extends Remote {
    
    public boolean cadastrarUsuario(Usuario usuario) throws RemoteException;
    public boolean cadastrarPost(Post post) throws RemoteException;
    public boolean cadastrarComentario(Comentario comentario) throws RemoteException;
    public boolean atualizarUsuario(Usuario usuario) throws RemoteException;
    public boolean atualizarPost(Post post) throws RemoteException;
    public List<Usuario> listarUsuarios() throws RemoteException;
    public List<Comentario> listarComentarios(int idPost) throws RemoteException;
    public List<Post> listarPosts() throws RemoteException;
    public List<Post> listarMeusPosts(int id) throws RemoteException;
    public Usuario login(String email) throws RemoteException;
    public Post buscarPostPorId(int idPost) throws RemoteException;
    public List<Post> buscarPostPorUsuario(int id) throws RemoteException;
    public List<Usuario> listarUsuarioNome(String filtro) throws RemoteException;
    public Usuario buscarUsuarioPorNome(String nome) throws RemoteException;
    public boolean excluirUsuario(Usuario usuario) throws RemoteException;
    public boolean excluirComentario(Comentario comentario) throws RemoteException;
}