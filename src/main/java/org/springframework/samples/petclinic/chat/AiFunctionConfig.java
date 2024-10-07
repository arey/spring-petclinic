package org.springframework.samples.petclinic.chat;

import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@Configuration
class AiFunctionConfig {

	private static final Logger LOG = LoggerFactory.getLogger(AiFunctionConfig.class);

	private final OwnerRepository owners;

	public AiFunctionConfig(OwnerRepository ownerRepository) {
		this.owners = ownerRepository;
	}

	@Bean
	@Description("""
			Query the owners by last name, the owner information include owner id, address, telephone, city, first name and last name.
			The owner also include the pets information, include the pet name, pet type and birth.
			The pet includes serveral visit record, includes the visit name and visit date.
			""")
	public Function<OwnerQueryRequest, List<Owner>> queryOwners() {
		return query -> {
			LOG.debug("Call function queryOwners with lastName={}", query.lastName());
			Pageable pageable = PageRequest.of(0, 10);
			return owners.findByLastName(query.lastName(), pageable).toList();
		};
	}

}

record OwnerQueryRequest(String lastName) {
}
