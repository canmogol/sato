from io.sato.plugin import Plugin


class UserDBPlugin(Plugin):
    def findLecturesOfUser(self, name, number):
        print("Python Hello {}, {}".format(name, number))
        return {"Calculus": 4, "Physics": 3, "Chemistry": 3, "Biology": 3}

    def documentation(self):
        return "Python UserDBPlugin"
