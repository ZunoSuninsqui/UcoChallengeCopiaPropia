package co.edu.uco.ucochallenge.secondary.adapters.repository.entity;

import java.util.UUID;

import co.edu.uco.ucochallenge.crosscuting.helper.TextHelper;
import co.edu.uco.ucochallenge.crosscuting.helper.UUIDHelper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Pais")
public class CountryEntity {

	@Id
	@Column(name = "id")
	private UUID id;

	@Column(name = "nombre")
	private String name;

	protected CountryEntity() {
		setId(UUIDHelper.getDefault());
		setName(TextHelper.getDefault());
	}

	private CountryEntity(final Builder builder) {
		setId(builder.id);
		setName(builder.name);
	}

	public static final class Builder {
		private UUID id;
		private String name;

		public Builder id(final UUID id) {
			this.id = id;
			return this;
		}

		public Builder name(final String name) {
			this.name = name;
			return this;
		}

		public CountryEntity build() {
			return new CountryEntity(this);
		}
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private void setId(final UUID id) {
		this.id = UUIDHelper.getDefault(id);
	}

	private void setName(final String name) {
		this.name = TextHelper.getDefaultWithTrim(name);
	}
}