import kotlin.math.roundToInt

data class Employee(
    val name: String?,
    val salary: Double,
    val bonusRate: Double
) {
    val bonus: Double
        get() = salary * bonusRate
}

class InvalidInputException(message: String) : Exception(message)

fun main() {
    try {
        println("Введіть кількість працівників:")
        val employeeCount = readln().toIntOrNull() ?: throw InvalidInputException("Невірна кількість працівників")
        
        if (employeeCount <= 0) {
            throw InvalidInputException("Кількість працівників повинна бути більше 0")
        }

        val employees = mutableListOf<Employee>()

        for (i in 1..employeeCount) {
            println("\nПрацівник #$i")
            
            println("Введіть ім'я (можна пропустити):")
            val name = readln().takeIf { it.isNotBlank() }
            
            println("Введіть оклад:")
            val salary = readln().toDoubleOrNull() ?: throw InvalidInputException("Невірний оклад")
            if (salary < 0) throw InvalidInputException("Оклад не може бути від'ємним")
            
            println("Введіть коефіцієнт бонусу (від 0.1 до 2.0):")
            val bonusRate = readln().toDoubleOrNull() ?: throw InvalidInputException("Невірний коефіцієнт бонусу")
            if (bonusRate < 0.1 || bonusRate > 2.0) throw InvalidInputException("Коефіцієнт бонусу повинен бути від 0.1 до 2.0")
            
            employees.add(Employee(name, salary, bonusRate))
        }

        if (employees.isNotEmpty()) {
            val maxBonusEmployee = employees.maxByOrNull { it.bonus }
            println("\nНайбільший бонус отримає: ${maxBonusEmployee?.name ?: "Без імені"} (${maxBonusEmployee?.bonus?.roundToInt()} грн)")

            val totalBonus = employees.sumOf { it.bonus }
            println("Загальна сума бонусів: ${totalBonus.roundToInt()} грн")

            val excellentCount = employees.count { it.bonus >= 10000 }
            println("Кількість працівників з бонусом ≥ 10000: $excellentCount")
        }

    } catch (e: InvalidInputException) {
        println("Помилка: ${e.message}")
    } catch (e: Exception) {
        println("Сталася невідома помилка: ${e.message}")
    }
} 