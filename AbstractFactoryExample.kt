interface Character {
    fun attack(): String
}

interface Weapon {
    fun use(): String
}

class Warrior : Character {
    override fun attack() = "Warrior swings a sword!"
}

class Mage : Character {
    override fun attack() = "Mage casts a fireball!"
}

class Sword : Weapon {
    override fun use() = "Swinging a sword!"
}

class Staff : Weapon {
    override fun use() = "Wielding a magical staff!"
}

interface GameFactory {
    fun createCharacter(): Character
    fun createWeapon(): Weapon
}

class MedievalGameFactory : GameFactory {
    override fun createCharacter(): Character = Warrior()
    override fun createWeapon(): Weapon = Sword()
}

class FantasyGameFactory : GameFactory {
    override fun createCharacter(): Character = Mage()
    override fun createWeapon(): Weapon = Staff()
}

fun main() {
    val medievalFactory: GameFactory = MedievalGameFactory()
    val warrior = medievalFactory.createCharacter()
    val sword = medievalFactory.createWeapon()

    println(warrior.attack()) 
    println(sword.use())

    val fantasyFactory: GameFactory = FantasyGameFactory()
    val mage = fantasyFactory.createCharacter()
    val staff = fantasyFactory.createWeapon()

    println(mage.attack()) 
    println(staff.use()) 
}
 
