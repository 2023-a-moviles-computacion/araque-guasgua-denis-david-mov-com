import java.io.*
import java.text.SimpleDateFormat
import java.util.*

data class Mascota(val id: Int, val nombre: String, val fechaNacimiento: Date, val peso: Float, val dueñoId: Int)

data class Dueño(val id: Int, val nombre: String, val numeroMascotas: Int, val fechaNacimiento: Date, val activo: Boolean, val salario: Float, val mascotas: List<Mascota>)

object DueñoDAO {
    private const val FILE_PATH = "D:\\Desktop\\MOVILES\\dueños.txt"
    private fun isDueñoIdExists(id: Int): Boolean {
        val dueños = readAll()
        return dueños.any { it.id == id }
    }

    fun readAll(): MutableList<Dueño> {
        val dueños = mutableListOf<Dueño>()
        try {
            val file = File(FILE_PATH)
            if (!file.exists()) {
                return dueños
            }
            val reader = BufferedReader(FileReader(file))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val tokens = line!!.split(",")
                val id = tokens[0].toInt()
                val nombre = tokens[1]
                val numeroMascotas = tokens[2].toInt()
                val fechaNacimiento = SimpleDateFormat("yyyy-MM-dd").parse(tokens[3])
                val activo = tokens[4].toBoolean()
                val salario = tokens[5].toFloat()

                val mascotas = MascotaDAO.readAll().filter { it.dueñoId == id }

                val dueño = Dueño(id, nombre, numeroMascotas, fechaNacimiento, activo, salario, mascotas)
                dueños.add(dueño)
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return dueños
    }

    fun create(dueño: Dueño) {
        val idExists = isDueñoIdExists(dueño.id)
        if (idExists) {
            throw IllegalArgumentException("El ID del dueño ya existe.")
            // O muestra un mensaje de error y regresa sin realizar ninguna acción adicional
        }
        val dueños = readAll()
        dueños.add(dueño)
        saveToFile(dueños)
    }

    fun update(dueño: Dueño) {
        val dueños = readAll()
        val existingDueño = dueños.find { it.id == dueño.id }
        if (existingDueño != null) {
            dueños.remove(existingDueño)
            dueños.add(dueño)
            saveToFile(dueños)
        }
    }

    fun delete(dueño: Dueño) {
        val dueños = readAll()
        dueños.remove(dueño)
        saveToFile(dueños)
    }

    private fun saveToFile(dueños: List<Dueño>) {
        val file = File(FILE_PATH)
        val writer = BufferedWriter(FileWriter(file))
        for (dueño in dueños) {
            writer.write("${dueño.id},${dueño.nombre},${dueño.numeroMascotas}," +
                    "${SimpleDateFormat("yyyy-MM-dd").format(dueño.fechaNacimiento)}," +
                    "${dueño.activo},${dueño.salario}")
            for (mascota in dueño.mascotas) {
                writer.write(",${mascota.id}:${mascota.nombre}:${SimpleDateFormat("yyyy-MM-dd").format(mascota.fechaNacimiento)}:${mascota.peso}:${mascota.dueñoId}")
            }
            writer.newLine()
        }
        writer.close()
    }
}
object MascotaDAO {
    private const val FILE_PATH = "D:\\Desktop\\MOVILES\\mascotas.txt"
    private fun isMascotaIdExists(id: Int): Boolean {
        val mascotas = readAll()
        return mascotas.any { it.id == id }
    }


    fun readAll(): MutableList<Mascota> {
        val mascotas = mutableListOf<Mascota>()
        try {
            val file = File(FILE_PATH)
            if (!file.exists()) {
                return mascotas
            }
            val reader = BufferedReader(FileReader(file))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val tokens = line!!.split(",")
                val id = tokens[0].toInt()
                val nombre = tokens[1]
                val fechaNacimiento = SimpleDateFormat("yyyy-MM-dd").parse(tokens[2])
                val peso = tokens[3].toFloat()
                val dueñoId = tokens[4].toInt()
                val mascota = Mascota(id, nombre, fechaNacimiento, peso, dueñoId)
                mascotas.add(mascota)
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return mascotas
    }

    fun create(mascota: Mascota) {
        val idExists = isMascotaIdExists(mascota.id)
        if (idExists) {
            throw IllegalArgumentException("El ID de la mascota ya existe.")
            // O muestra un mensaje de error y regresa sin realizar ninguna acción adicional
        }
        val mascotas = readAll()
        mascotas.add(mascota)
        saveToFile(mascotas)
    }

    fun update(mascota: Mascota) {
        val mascotas = readAll()
        val existingMascota = mascotas.find { it.id == mascota.id }
        if (existingMascota != null) {
            mascotas.remove(existingMascota)
            mascotas.add(mascota)
            saveToFile(mascotas)
        }
    }

    fun delete(mascota: Mascota) {
        val mascotas = readAll()
        mascotas.remove(mascota)
        saveToFile(mascotas)
    }

    private fun saveToFile(mascotas: List<Mascota>) {
        val file = File(FILE_PATH)
        val writer = BufferedWriter(FileWriter(file))
        for (mascota in mascotas) {
            writer.write("${mascota.id},${mascota.nombre}," +
                    "${SimpleDateFormat("yyyy-MM-dd").format(mascota.fechaNacimiento)}," +
                    "${mascota.peso},${mascota.dueñoId}")
            writer.newLine()
        }
        writer.close()
    }
}

fun main() {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val scanner = Scanner(System.`in`)

    while (true) {
        println("--- MENU ---")
        println("1. Ver Dueños")
        println("2. Agregar Dueño")
        println("3. Editar Dueño")
        println("4. Eliminar Dueño")
        println("5. Ver Mascotas")
        println("6. Agregar Mascota")
        println("7. Editar Mascota")
        println("8. Eliminar Mascota")
        println("9. Salir")
        print("Ingrese una opción: ")
        val option = scanner.nextLine()

        when (option) {
            "1" -> {
                val dueños = DueñoDAO.readAll()
                println("--- LISTA DE DUEÑOS ---")
                for (dueño in dueños) {
                    println("ID: ${dueño.id}")
                    println("Nombre: ${dueño.nombre}")
                    println("Número de Mascotas: ${dueño.numeroMascotas}")
                    println("Fecha de Nacimiento: ${dateFormat.format(dueño.fechaNacimiento)}")
                    println("Activo: ${dueño.activo}")
                    println("Salario: ${dueño.salario}")
                    println("Mascotas:")
                    for (mascota in dueño.mascotas) {
                        println("\tID: ${mascota.id}")
                        println("\tNombre: ${mascota.nombre}")
                        println("\tFecha de Nacimiento: ${dateFormat.format(mascota.fechaNacimiento)}")
                        println("\tPeso: ${mascota.peso}")
                        println("\tDueño ID: ${mascota.dueñoId}")
                    }
                    println("---------------")
                }
            }
            "2" -> {
                println("--- AGREGAR DUEÑO ---")
                println("Ingrese el ID del dueño:")
                val id = scanner.nextInt()
                scanner.nextLine() // Consume the newline character
                println("Ingrese el nombre del dueño:")
                val nombre = scanner.nextLine()
                println("Ingrese el número de mascotas:")
                val numeroMascotas = scanner.nextInt()
                scanner.nextLine() // Consume the newline character
                println("Ingrese la fecha de nacimiento del dueño (yyyy-MM-dd):")
                val fechaNacimiento = dateFormat.parse(scanner.nextLine())
                println("Ingrese si el dueño está activo (true/false):")
                val activo = scanner.nextBoolean()
                scanner.nextLine() // Consume the newline character
                println("Ingrese el salario del dueño:")
                val salario = scanner.nextFloat()
                scanner.nextLine() // Consume the newline character

                val dueño = Dueño(id, nombre, numeroMascotas, fechaNacimiento, activo, salario, emptyList())
                DueñoDAO.create(dueño)
                println("El dueño se ha registrado exitosamente.")
            }
            "3" -> {
                println("--- EDITAR DUEÑO ---")
                println("Ingrese el ID del dueño a editar:")
                val id = scanner.nextInt()
                scanner.nextLine() // Consume the newline character
                val dueño = DueñoDAO.readAll().find { it.id == id }
                if (dueño != null) {
                    println("Ingrese el nuevo nombre del dueño:")
                    val nombre = scanner.nextLine()
                    println("Ingrese el nuevo número de mascotas:")
                    val numeroMascotas = scanner.nextInt()
                    scanner.nextLine() // Consume the newline character
                    println("Ingrese la nueva fecha de nacimiento del dueño (yyyy-MM-dd):")
                    val fechaNacimiento = dateFormat.parse(scanner.nextLine())
                    println("Ingrese si el dueño está activo (true/false):")
                    val activo = scanner.nextBoolean()
                    scanner.nextLine() // Consume the newline character
                    println("Ingrese el nuevo salario del dueño:")
                    val salario = scanner.nextFloat()
                    scanner.nextLine() // Consume the newline character

                    val newDueño = Dueño(id, nombre, numeroMascotas, fechaNacimiento, activo, salario, dueño.mascotas)
                    DueñoDAO.update(newDueño)
                    println("El dueño se ha actualizado exitosamente.")
                } else {
                    println("No se encontró un dueño con el ID proporcionado.")
                }
            }
            "4" -> {
                println("--- ELIMINAR DUEÑO ---")
                println("Ingrese el ID del dueño a eliminar:")
                val id = scanner.nextInt()
                scanner.nextLine() // Consume the newline character
                val dueño = DueñoDAO.readAll().find { it.id == id }
                if (dueño != null) {
                    DueñoDAO.delete(dueño)
                    println("El dueño se ha eliminado exitosamente.")
                } else {
                    println("No se encontró un dueño con el ID proporcionado.")
                }
            }
            "5" -> {
                // Ver Mascotas
                val mascotas = MascotaDAO.readAll()
                println("--- LISTA DE MASCOTAS ---")
                for (mascota in mascotas) {
                    println("ID: ${mascota.id}")
                    println("Nombre: ${mascota.nombre}")
                    println("Fecha de Nacimiento: ${dateFormat.format(mascota.fechaNacimiento)}")
                    println("Peso: ${mascota.peso}")
                    println("Dueño ID: ${mascota.dueñoId}")
                    println("---------------")
                }
            }
            "6" -> {
                // Agregar Mascota
                println("--- AGREGAR MASCOTA ---")
                println("Ingrese el ID de la mascota:")
                val id = scanner.nextInt()
                scanner.nextLine() // Consume the newline character
                println("Ingrese el nombre de la mascota:")
                val nombre = scanner.nextLine()
                println("Ingrese la fecha de nacimiento de la mascota (yyyy-MM-dd):")
                val fechaNacimiento = dateFormat.parse(scanner.nextLine())
                println("Ingrese el peso de la mascota:")
                val peso = scanner.nextFloat()
                scanner.nextLine() // Consume the newline character
                println("Ingrese el ID del dueño de la mascota:")
                val dueñoId = scanner.nextInt()
                scanner.nextLine() // Consume the newline character

                val mascota = Mascota(id, nombre, fechaNacimiento, peso, dueñoId)
                MascotaDAO.create(mascota)
                println("La mascota se ha registrado exitosamente.")
            }
            "7" -> {
                // Editar Mascota
                println("--- EDITAR MASCOTA ---")
                println("Ingrese el ID de la mascota a editar:")
                val id = scanner.nextInt()
                scanner.nextLine() // Consume the newline character
                val mascota = MascotaDAO.readAll().find { it.id == id }
                if (mascota != null) {
                    println("Ingrese el nuevo nombre de la mascota:")
                    val nombre = scanner.nextLine()
                    println("Ingrese la nueva fecha de nacimiento de la mascota (yyyy-MM-dd):")
                    val fechaNacimiento = dateFormat.parse(scanner.nextLine())
                    println("Ingrese el nuevo peso de la mascota:")
                    val peso = scanner.nextFloat()
                    scanner.nextLine() // Consume the newline character
                    println("Ingrese el nuevo ID del dueño de la mascota:")
                    val dueñoId = scanner.nextInt()
                    scanner.nextLine() // Consume the newline character

                    val newMascota = Mascota(id, nombre, fechaNacimiento, peso, dueñoId)
                    MascotaDAO.update(newMascota)
                    println("La mascota se ha actualizado exitosamente.")
                } else {
                    println("No se encontró una mascota con el ID proporcionado.")
                }
            }
            "8" -> {
                // Eliminar Mascota
                println("--- ELIMINAR MASCOTA ---")
                println("Ingrese el ID de la mascota a eliminar:")
                val id = scanner.nextInt()
                scanner.nextLine() // Consume the newline character
                val mascota = MascotaDAO.readAll().find { it.id == id }
                if (mascota != null) {
                    MascotaDAO.delete(mascota)
                    println("La mascota se ha eliminado exitosamente.")
                } else {
                    println("No se encontró una mascota con el ID proporcionado.")
                }
            }
            "9" -> {
                println("¡Hasta luego!")
                return
            }
            else -> println("Opción inválida. Por favor, ingrese una opción válida.")
        }
        println() // Print an empty line for separation
    }
}
