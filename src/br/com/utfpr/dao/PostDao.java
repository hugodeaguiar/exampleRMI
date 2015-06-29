package br.com.utfpr.dao;

import br.com.utfpr.pojo.Post;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author hugo
 */
public class PostDao {

    private EntityManager em;
    private EntityManagerFactory factory;

    public PostDao(EntityManager em, EntityManagerFactory factory) {
        this.em = em;
        this.factory = factory;
    }

    public List<Post> listarPost() {
        try {
            TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.ativo is true ORDER BY p.id DESC", Post.class);
            return query.getResultList();
        } catch (Exception e) {            
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public List<Post> listarMeusPosts(int id) {
        try {
            this.em = factory.createEntityManager();
            TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.usuario.id = ?1 ORDER BY p.id DESC", Post.class);
            query.setParameter(1, id);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Post buscarPorId(int id) {
        try {
            this.em = factory.createEntityManager();
            TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.id = ?1 AND p.ativo is true", Post.class);
            query.setParameter(1, id);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean cadastrarPost(Post post) {
        try {
            em.getTransaction().begin();
            em.persist(post);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println(e.toString());
            return false;
        }
        return true;
    }

    public boolean atualizarPost(Post post) {
        try {
            em.getTransaction().begin();
            em.merge(post);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println(e.toString());
            return false;
        }
        return true;
    }

    public boolean inativarPost(int id) {
        try {
            em = factory.createEntityManager();
            Query query = em.createNativeQuery("UPDATE post SET ativo = FALSE WHERE id = ?1", Post.class);
            query.setParameter(1, id);
            
            em.getTransaction().begin();
                query.executeUpdate();
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean excluirPostPorUsuario(int id) {
        try{
            em = factory.createEntityManager();
            Query query = em.createNativeQuery("DELETE FROM post WHERE idUsuario = ?1", Post.class);
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
    
    public List<Post> listarPostsPorUsuario(int id) {
        try {
            this.em = factory.createEntityManager();
            TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.ativo is true AND p.usuario.id = ?1 ORDER BY p.id DESC", Post.class);
            query.setParameter(1, id);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
