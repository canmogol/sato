package io.sato.plugin;

public interface Plugin {

    /**
     * Result of the plugin execution in JSON format, such as:
     * <pre>
     *     {
     *         "users" : ["john","root"],
     *         "server_id" : "321",
     *     }
     * </pre>
     *
     * @param objects may be null
     * @return result as JSON
     */
    String execute(Object... objects);

    /**
     * Documentation of this plugin, should explain the purpose and usage of the plugin
     *
     * @return description and usage
     */
    String documentation();

}
