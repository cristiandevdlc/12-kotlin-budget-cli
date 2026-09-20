import java.io.File

data class Entry(val type: String, val amount: Double, val label: String)
val file = File("budget.tsv")
fun load() = if (file.exists()) file.readLines().filter { it.isNotBlank() }.map { val p = it.split('\t'); Entry(p[0], p[1].toDouble(), p[2]) }.toMutableList() else mutableListOf()
fun main(args: Array<String>) {
    val entries = load()
    when (args.getOrNull(0)) {
        "income", "expense" -> { val amount = args.getOrNull(1)?.toDoubleOrNull() ?: error("Importe inválido"); val label = args.getOrNull(2) ?: "general"; entries += Entry(args[0], amount, label); file.writeText(entries.joinToString("\n") { "${it.type}\t${it.amount}\t${it.label}" }); println("Movimiento guardado") }
        "summary" -> { val income = entries.filter { it.type == "income" }.sumOf { it.amount }; val expense = entries.filter { it.type == "expense" }.sumOf { it.amount }; println("Ingresos: %.2f\nGastos: %.2f\nBalance: %.2f".format(income, expense, income - expense)) }
        else -> println("Uso: income IMPORTE ETIQUETA | expense IMPORTE ETIQUETA | summary")
    }
}
