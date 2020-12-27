// entry point

fun main(args: Array<String>) {
    val app = simple.sample.App()
    app.migrate()
    app.start(8080)
}
