package co.edu.uco.messageservice.catalog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageCatalog {

	private static final Map<String, Message> messages = new ConcurrentHashMap<>();

	static {
		register("exception.general.unexpected",
				"Ha ocurrido un error inesperado. Por favor, intente nuevamente más tarde.");
		register("exception.general.technical", "Se produjo un error interno al procesar la solicitud.");
		register("exception.general.user", "No fue posible procesar la solicitud con la información recibida.");

		register("register.user.success", "Usuario registrado correctamente.");
		register("register.user.validation.idType.required", "El tipo de identificación es obligatorio.");
		register("register.user.validation.idType.notFound",
				"El tipo de identificación indicado no existe en el sistema.");
		register("register.user.validation.idNumber.required", "El número de identificación es obligatorio.");
		register("register.user.validation.idNumber.invalidFormat",
				"El número de identificación solo puede contener dígitos.");
		register("register.user.validation.idNumber.length",
				"El número de identificación debe tener entre 5 y 20 dígitos.");
		register("register.user.validation.name.required", "El nombre suministrado es obligatorio.");
		register("register.user.validation.name.invalidCharacters", "El nombre solo puede contener letras y espacios.");
		register("register.user.validation.name.length", "El nombre debe tener entre 2 y 40 caracteres.");
		register("register.user.validation.homeCity.required", "La ciudad de residencia es obligatoria.");
		register("register.user.validation.homeCity.notFound",
				"La ciudad de residencia indicada no existe en el sistema.");
		register("register.user.validation.email.required", "El correo electrónico es obligatorio.");
		register("register.user.validation.email.length",
				"El correo electrónico debe tener entre 10 y 100 caracteres.");
		register("register.user.validation.email.invalidFormat", "El formato del correo electrónico no es válido.");
		register("register.user.validation.mobile.required", "El número de teléfono móvil es obligatorio.");
		register("register.user.validation.mobile.invalidFormat",
				"El número de teléfono móvil debe contener exactamente 10 dígitos.");

		register("register.user.rule.id.duplicated",
				"Se detectó un conflicto con el identificador del usuario; se generará uno nuevo.");
		register("register.user.rule.idTypeNumber.duplicated.admin",
				"Existe un usuario con el mismo tipo y número de identificación. Se notificará al administrador.");
		register("register.user.rule.idTypeNumber.duplicated.executor",
				"Ya existe un usuario registrado con ese tipo y número de identificación.");
		register("register.user.rule.email.duplicated.owner",
				"El correo electrónico suministrado ya está registrado; se notificará al propietario.");
		register("register.user.rule.email.duplicated.executor",
				"Ya existe un usuario con el correo electrónico proporcionado.");
		register("register.user.rule.mobile.duplicated.owner",
				"El número de teléfono suministrado ya está registrado; se notificará al propietario.");
		register("register.user.rule.mobile.duplicated.executor",
				"Ya existe un usuario con el número de teléfono proporcionado.");
		register("register.user.rule.email.confirmation.strategy",
				"Se enviará la estrategia de confirmación del correo electrónico.");
		register("register.user.rule.email.confirmation.pending",
				"El correo electrónico debe ser confirmado para finalizar el registro.");
		register("register.user.rule.mobile.confirmation.strategy",
				"Se enviará la estrategia de confirmación del número móvil.");
		register("register.user.rule.mobile.confirmation.pending",
				"El número móvil debe ser confirmado para finalizar el registro.");
		register("list.users.validation.page.negative", "La página solicitada no puede ser negativa.");
		register("list.users.validation.size.invalid", "El tamaño de página debe estar entre 1 y 50 registros.");
	}
	}

	public static Message getMessageValue(String key) {
		return messages.get(key);
	}

	public static void synchronizeMessageValue(Message message) {
		register(message.getKey(), message.getValue());
	}

	public static Message removeMessage(String key) {
		return messages.remove(key);
	}

	public static Map<String, Message> getAllMessages() {
		return messages;
	}

	private static void register(final String key, final String value) {
		messages.put(key, new Message(key, value));
	}

}