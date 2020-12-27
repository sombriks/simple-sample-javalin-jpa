package simple.sample.controller

import io.javalin.http.Context
import simple.sample.model.Product
import javax.persistence.EntityManagerFactory

class ProductController(val emf: EntityManagerFactory) {

    fun listAll(ctx: Context) {
        val em = emf.createEntityManager()
        ctx.json(em.createQuery("select p from Product p").resultList)
        em.close()
    }

    fun findOne(ctx: Context) {
        val em = emf.createEntityManager()
        try {
            val p = em.createQuery("select p from Product p where p.id = :id")
                .setParameter("id", ctx.pathParam("id").toLong()).singleResult
            ctx.json(p)
        } finally {
            em.close()
        }
    }
}