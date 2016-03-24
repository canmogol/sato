import io.sato.Plugin

class UserDBPlugin implements Plugin {

    Map findLecturesOfUser(String name, Long number) {
        println("Groovy Hello " + name + ", " + number)
        def map = [:]
        map["Calculus"] = 4
        map["Physics"] = 3
        map["Chemistry"] = 3
        map["Biology"] = 3
        return map
    }

    @Override
    String documentation() {
        return "Groovy UserDBPlugin"
    }
}