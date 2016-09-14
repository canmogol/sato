class UserDBPlugin
  include Java::io.sato.plugin.Plugin

  def findLecturesOfUser(name, age)
    puts "Ruby Hello #{name}, #{age}"
    Hash["Calculus" => 4, "Physics" => 3, "Chemistry" => 3, "Biology" => 3]
  end

  def documentation
    'Ruby UserDBPlugin'
  end

end
UserDBPlugin.new
