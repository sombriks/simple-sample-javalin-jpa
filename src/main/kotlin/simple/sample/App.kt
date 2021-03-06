package simple.sample

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import org.flywaydb.core.Flyway
import simple.sample.controller.ProductController
import simple.sample.controller.UserController

class App {

    val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("sample-pu")
    val userController: UserController = UserController(emf)
    val productController: ProductController = ProductController(emf)

    val app: Javalin = Javalin.create { config ->
        config.defaultContentType = "application/json"
        config.addStaticFiles("/public")
        config.enableCorsForAllOrigins()
    }.routes {
        get("/") { ctx -> ctx.result("ONLINE") }
        path("users") {
            get(userController::listAll)
            get(":id", userController::findOne)
            post(userController::insert)
            put(":id",userController::update)
            delete(":id",userController::del)
        }
        path("products") {
            get(productController::listAll)
            get(":id", productController::findOne)

        }
    }

    fun start(port: Int) {
        app.start(port)
    }

    fun migrate() {
        val flyway = Flyway.configure().dataSource("jdbc:h2:file:~/sample-javalin", "sa", "").load()
        flyway.migrate()
    }
}