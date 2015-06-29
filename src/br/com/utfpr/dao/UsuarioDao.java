/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.utfpr.dao;

import br.com.utfpr.pojo.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author hugo
 */
public class UsuarioDao {
    private EntityManager em;
    private EntityManagerFactory factory;
    
   public UsuarioDao(EntityManager em, EntityManagerFactory factory){
        this.em = em;
        this.factory = factory;
   }
   
   public List<Usuario> listarUsuario(){
        try{
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u ORDER BY u.id DESC", Usuario.class);
            return query.getResultList();
        }catch(Exception e){
            return null;
        }
    }
   
   public List<Usuario> listarPorNome(String nome){
        try{
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.nome like ?1", Usuario.class);
            query.setParameter(1, "%" + nome + "%");
            return query.getResultList();
        }catch(Exception e){
            return null;
        }
    }
   
    public Usuario buscarPorId(int id){
        try{
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.id = ?1", Usuario.class);
            query.setParameter(1, id);
            return query.getSingleResult();
        }catch(Exception e){
            return null;
        }
    }
    
   public Usuario buscarPorNome(String nome){
        try{
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.nome = ?1", Usuario.class);
            query.setParameter(1, nome);
            return query.getSingleResult();
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean cadastrarUsuario(Usuario usuario){
        System.out.println("cadastrando usuário");
        try{
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        }catch(Exception e){
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            System.out.println(e.toString());
            return false;
        }
        return true;
    }
    
    public boolean atualizarUsuario(Usuario usuario){
        try{
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        }catch(Exception e){
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            
            System.out.println(e.toString());
            return false;
        }
        return true;
    }
    
    public boolean excluirUsuario(Usuario usuario){
        try{
           Usuario usu  = this.buscarPorId(usuario.getId());
           
           if(usu != null) {
                em.getTransaction().begin();
                em.remove(usu);
                em.getTransaction().commit();
                return true;
           } else {
               return false;
           }
          
        }catch(Exception e){
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            
            System.out.println(e.toString());
            return false;
        }
    }
    
    public Usuario buscarPorEmail(String email) {
        System.out.println("buscar por email");
        try{
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = ?1", Usuario.class);
            query.setParameter(1, email);
            return query.getSingleResult();
        }catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
    }
 }
