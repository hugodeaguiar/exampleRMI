/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.utfpr.dao;

import br.com.utfpr.pojo.Comentario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author hugo
 */
public class ComentarioDao {
    
    private EntityManager em;
    private EntityManagerFactory factory;
    
    public ComentarioDao(EntityManager em, EntityManagerFactory factory){
        this.em = em;
        this.factory = factory;
    }
    
    public List<Comentario> listarComentario(int idPost){
        try{
            TypedQuery<Comentario> query = em.createQuery("SELECT c FROM Comentario c WHERE c.post.id = ?1 ORDER BY c.id DESC", Comentario.class);
            query.setParameter(1, idPost);
            return query.getResultList();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public Comentario buscarPorId(int id){
        try{
            TypedQuery<Comentario> query = em.createQuery("SELECT c FROM Comentario c WHERE c.id = ?1", Comentario.class);
            query.setParameter(1, id);
            return query.getSingleResult();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public boolean cadastrarComentario(Comentario comentario){
        try{
            em.getTransaction().begin();
            em.persist(comentario);
            em.getTransaction().commit();
        }catch(Exception e){
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            
            System.out.println(e.toString());
            return false;
        }
        return true;
    }
    
    public boolean atualizarComentario(Comentario comentario){
        try{
            em.getTransaction().begin();
            em.merge(comentario);
            em.getTransaction().commit();
        }catch(Exception e){
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            
            System.out.println(e.toString());
            return false;
        }
        return true;
    }
    
    public boolean excluirComentario(Comentario comentario){
        try{
            Comentario com = this.buscarPorId(comentario.getId());
            
            if(com != null) {
                em.getTransaction().begin();
                em.remove(com);
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
    
    public boolean excluirComentarioPorUsuario(int id) {
        try{
            Query query = em.createNativeQuery("DELETE FROM comentario WHERE idUsuario = ?1", Comentario.class);
            query.setParameter(1, id);
            
            em.getTransaction().begin();
                query.executeUpdate();
            em.getTransaction().commit();
            return true;
        }catch(Exception e) {            
             if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            
            System.out.println(e.getMessage());
            return false;
        }
    }
}
