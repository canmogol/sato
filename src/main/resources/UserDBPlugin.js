var System = Java.type('java.lang.System');

function UserDBPlugin() {
    this.findLecturesOfUser = function (name, number) {
        System.out.println('Javascript Hello ' + name + ' ' + number);
        var lectures = {Calculus: 4, Physics: 3, Chemistry: 3, Biology: 3};
        return lectures;
    };
    this.documentation = function () {
        return 'Javascript UserDBPlugin';
    };
}

function instance() {
    return new UserDBPlugin();
}
