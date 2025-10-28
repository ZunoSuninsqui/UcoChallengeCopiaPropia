package co.edu.uco.messageservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.messageservice.catalog.Message;
import co.edu.uco.messageservice.catalog.MessageCatalog;

@RestController
@RequestMapping("/messages/api/v1/messages")
public class MessageController {

	@GetMapping("/{key}")
	public ResponseEntity<Message> getMessage(@PathVariable String key) {

		var value = MessageCatalog.getMessageValue(key);

		return new ResponseEntity<>(value, (value == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK);

	}

	@PutMapping("/{key}")
	public ResponseEntity<Message> modifyMessage(@PathVariable String key, @RequestBody Message value) {

		value.setKey(key);
		MessageCatalog.synchronizeMessageValue(value);
		return new ResponseEntity<>(value, HttpStatus.OK);

	}

}
