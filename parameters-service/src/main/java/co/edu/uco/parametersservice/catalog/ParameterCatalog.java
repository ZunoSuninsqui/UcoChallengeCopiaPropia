package co.edu.uco.parametersservice.catalog;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParameterCatalog {

	private static final Map<String, Parameter> parameters = new ConcurrentHashMap<>();

	static {
		
		register("notification.admin.email", "admin@uco.edu.co");
        register("notification.duplicated.email.template",
                                "Hola %s, detectamos un intento de registro con su correo electrónico. Si no ha sido usted, por favor contacte al administrador.");
        register("notification.duplicated.mobile.template",
                                "Hola %s, detectamos un intento de registro con su número móvil. Si no ha sido usted, comuníquese con soporte.");
        register("notification.confirmation.email.strategy", "ENVIAR_LINK_CONFIRMACION");
        register("notification.confirmation.mobile.strategy", "ENVIAR_CODIGO_SMS");
        register("notification.email.maxRetries", "3");
        register("validation.code.timeExpiration", "5"); // in minutes
    }

    public static Parameter getParameterValue(String key) {
    	
    	return parameters.get(key);
    
    }

    public static void synchronizeParameterValue(Parameter parameter) {
    	register(parameter.getKey(), parameter.getValue());
    }

    public static Parameter removeParameter(String key) {
    	return parameters.remove(key);
    }

    public static Map<String, Parameter> getAllParameters() {
    	return parameters;
    }

    private static void register(final String key, final String value) {
    	parameters.put(key, new Parameter(key, value));
    }

}